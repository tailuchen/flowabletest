package com.oceandata.tailuchen.flowabletest;

import org.flowable.engine.impl.db.DbIdGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowabletestApplication {


    public static void main(String[] args) {
        SpringApplication.run(FlowabletestApplication.class, args);
    }

}

