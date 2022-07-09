package com.wiki.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiki.mywiki.domain.Ebook;
import com.wiki.mywiki.domain.EbookExample;
import com.wiki.mywiki.mapper.EbookMapper;
import com.wiki.mywiki.req.EbookQueryReq;
import com.wiki.mywiki.resp.EbookQueryResp;
import com.wiki.mywiki.req.EbookSaveReq;
import com.wiki.mywiki.resp.PageResp;
import com.wiki.mywiki.util.CopyUtil;
import com.wiki.mywiki.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);
    @Resource
    private EbookMapper ebookMapper;
    private UuidUtils uuidUtils;
    public List<Ebook> list(){
        return ebookMapper.selectByExample(null);
    }
    public PageResp<EbookQueryResp> list(EbookQueryReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        // 只对下面遇到的第一个sql起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList =  ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EbookSaveReq req){
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            // 新增
            ebook.setId(uuidUtils.getId());
            ebook.setDocCount(0);
            ebook.setViewCount(0);
            ebook.setVoteCount(0);
            ebookMapper.insert(ebook);
        }else{
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id){
        ebookMapper.deleteByPrimaryKey(id);
    }
}
