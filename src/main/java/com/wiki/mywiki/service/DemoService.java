package com.wiki.mywiki.service;

import com.wiki.mywiki.domain.Demo;
import com.wiki.mywiki.domain.Test;
import com.wiki.mywiki.mapper.DemoMapper;
import com.wiki.mywiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {
    @Resource
    private DemoMapper demoMapper;
    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}
