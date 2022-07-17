package com.wiki.mywiki.controller;

import com.wiki.mywiki.domain.Test;
import com.wiki.mywiki.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    // 找到名字是test.hello的配置项
    @Value("${test.hello:TEST}")
    private String testH;
    @Resource
    private TestService testService;
    @Resource
    private RedisTemplate redisTemplate;
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

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable Long key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }
}
