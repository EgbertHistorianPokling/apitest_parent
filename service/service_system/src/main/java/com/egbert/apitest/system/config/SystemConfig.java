package com.egbert.apitest.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.egbert.apitest.system.mapper")
public class SystemConfig {
}
