package com.wiki.mywiki.controller;

import com.wiki.mywiki.domain.Demo;
import com.wiki.mywiki.domain.Test;
import com.wiki.mywiki.service.DemoService;
import com.wiki.mywiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private DemoService demoService;

    @PostMapping("post")
    public String helloPost(String name, String content){
        return name + " " + content;
    }

    @GetMapping("list1")
            public List<Demo> list(){
        return demoService.list();
    }

}
