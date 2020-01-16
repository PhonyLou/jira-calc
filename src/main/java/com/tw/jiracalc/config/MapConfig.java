package com.tw.jiracalc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "jira")
@EnableConfigurationProperties(MapConfig.class)
@Data
public class MapConfig {
    private Map<String, String> jql;
    private Map<String, String> stage;
}
