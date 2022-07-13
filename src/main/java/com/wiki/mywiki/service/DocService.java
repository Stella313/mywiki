package com.wiki.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiki.mywiki.domain.Doc;
import com.wiki.mywiki.domain.DocExample;
import com.wiki.mywiki.mapper.DocMapper;
import com.wiki.mywiki.req.DocQueryReq;
import com.wiki.mywiki.req.DocSaveReq;
import com.wiki.mywiki.resp.DocQueryResp;
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
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);
    @Resource
    private DocMapper docMapper;
    private UuidUtils uuidUtils;
    public List<Doc> list(){
        return docMapper.selectByExample(null);
    }

    public List<DocQueryResp> all(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList =  docMapper.selectByExample(docExample);
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
        return list;
    }

    public PageResp<DocQueryResp> list(DocQueryReq req){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        // 只对下面遇到的第一个sql起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList =  docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req, Doc.class);
        if(ObjectUtils.isEmpty(req.getId())){
            // 新增
            doc.setId(uuidUtils.getId());
            docMapper.insert(doc);
        }else{
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }
    public void delete(List<String> ids){
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
