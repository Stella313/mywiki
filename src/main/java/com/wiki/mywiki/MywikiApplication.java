package com.wiki.mywiki;

import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MywikiApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MywikiApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MywikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("Start successfully!! ");
        LOG.info("Address: \thttp://127.0.0.1:{}", env.getProperty("sever.port"));
    }

}
