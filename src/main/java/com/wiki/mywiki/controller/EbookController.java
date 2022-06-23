package com.wiki.mywiki.controller;

import com.wiki.mywiki.domain.Ebook;
import com.wiki.mywiki.req.EbookReq;
import com.wiki.mywiki.resp.CommonResp;
import com.wiki.mywiki.resp.EbookResp;
import com.wiki.mywiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list =  ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

}
