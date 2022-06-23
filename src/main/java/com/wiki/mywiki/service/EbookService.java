package com.wiki.mywiki.service;

import com.wiki.mywiki.domain.Ebook;
import com.wiki.mywiki.domain.EbookExample;
import com.wiki.mywiki.mapper.EbookMapper;
import com.wiki.mywiki.req.EbookReq;
import com.wiki.mywiki.resp.EbookResp;
import com.wiki.mywiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;
    public List<Ebook> list(){
        return ebookMapper.selectByExample(null);
    }
    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList =  ebookMapper.selectByExample(ebookExample);
        return CopyUtil.copyList(ebookList, EbookResp.class);
    }
}
