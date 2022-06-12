package com.wiki.mywiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    @Value("${test.hello:TEST}")
    private String testH;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!" + " " + testH;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name, String content){
        return name + " " + content;
    }


}
