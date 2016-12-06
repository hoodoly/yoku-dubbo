package com.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.dubbo.resolver.DubboConsumerResolver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@ConditionalOnProperty(value = "dubbo.module", havingValue = "true")
@Configuration
@EnableAutoConfiguration
public class ConsumerAutoCofiguration {

    @Bean
    public DubboConsumerResolver dubboRpcConsumerResolver(ApplicationContext applicationContext,
                                                          ApplicationConfig applicationConfig,
                                                          RegistryConfig registryConfig,
                                                          ConsumerConfig consumerConfig) {
        return new DubboConsumerResolver(registryConfig, consumerConfig, applicationConfig, applicationContext);
    }
}
