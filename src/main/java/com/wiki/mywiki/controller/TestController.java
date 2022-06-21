package com.wiki.mywiki.controller;

import com.wiki.mywiki.domain.Test;
import com.wiki.mywiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    // 找到名字是test.hello的配置项
    @Value("${test.hello:TEST}")
    private String testH;
    @Resource
    private TestService testService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!" + " " + testH;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name, String content){
        return name + " " + content;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

}
