package com.dubbo.config;

import com.alibaba.dubbo.config.*;
import com.dubbo.command.DubboServiceLatchCommandLineRunner;
import com.dubbo.command.DubboServiceListener;
import com.dubbo.properties.DubboProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Desc:
 * Author:
 * Date: 2016/11/9
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnProperty(prefix = "dubbo", value = "enabled", matchIfMissing = true)
public class DubboBaseAutoConfiguration {

    private static final String SEPARATOR = File.separator;
    private final static Logger log = LoggerFactory.getLogger(DubboBaseAutoConfiguration.class);



    @Autowired
    DubboProperties dubboProperties;


    private String appName = "Dubbo";

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        if (StringUtils.isEmpty(dubboProperties.getModule()) && StringUtils.isEmpty(appName)) {
            throw new IllegalStateException("AppName can't be empty, please make sure that 'rpc.dubbo.module' " +
                    "or 'spring.application.name' have been set");
        }
        if (!StringUtils.isEmpty(dubboProperties.getModule())){
            appName = dubboProperties.getModule();
        }
        config.setName(appName);
        config.setLogger("slf4j");
        return config;
    }


    @Bean
    @ConditionalOnMissingBean(RegistryConfig.class)
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboProperties.getRegistryAddress());
        registryConfig.setProtocol(dubboProperties.getRegProtocol());
        registryConfig.setId("central");
        registryConfig.setTimeout(3000);
        registryConfig.setVersion(dubboProperties.getVersion());
        try {
            registryConfig.setFile(System.getenv("HOME") + SEPARATOR + ".dubbo" + SEPARATOR + "dubbo-" +
                    appName + "-" + InetAddress.getLocalHost().getHostAddress() + ".cache");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return registryConfig;

    }

    @Bean
    @ConditionalOnMissingBean(ProtocolConfig.class)
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setHost(dubboProperties.getHost());
        protocolConfig.setSerialization(dubboProperties.getSerialization());
        protocolConfig.setName("dubbo");

        if (dubboProperties.getPort() == -1) {
            int port = SocketUtils.findAvailableTcpPort(30000);
            protocolConfig.setPort(port);
        } else {
            protocolConfig.setPort(dubboProperties.getPort());
        }
        protocolConfig.setThreads(dubboProperties.getThreads());
        protocolConfig.setHeartbeat(dubboProperties.getHeartBeats());

        return protocolConfig;

    }

    @Bean
    @ConditionalOnMissingBean(ProviderConfig.class)
    public ProviderConfig providerConfig(RegistryConfig registryConfig, ProtocolConfig protocolConfig, ApplicationConfig applicationConfig){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setVersion(dubboProperties.getVersion());
        providerConfig.setTimeout(dubboProperties.getTimeout());
        providerConfig.setThreads(dubboProperties.getThreads());
        providerConfig.setHost(dubboProperties.getHost());
        providerConfig.setSerialization(dubboProperties.getSerialization());

        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        providerConfig.setApplication(applicationConfig);
        return providerConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig config = new ConsumerConfig();
        config.setCheck(dubboProperties.getCheck());
        config.setTimeout(dubboProperties.getTimeout());
        config.setVersion(dubboProperties.getVersion());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public DubboServiceLatchCommandLineRunner configureDubboServiceLatchCommandLineRunner() {
        return new DubboServiceLatchCommandLineRunner();
    }

    @Bean
    @ConditionalOnMissingBean
    public DubboServiceListener dubboServiceListenerBean() {
        log.info("Auto start dubbo configuration");
        log.info("module     --> {}", appName);
        log.info("port       --> {}", dubboProperties.getPort());
        log.info("registry   --> {}", getAddress());
        log.info("host       --> {}", dubboProperties.getHost());
        log.info("version    --> {}", dubboProperties.getVersion());
        return new DubboServiceListener(appName);
    }

    private String getAddress() {
        return dubboProperties.getRegProtocol() + "://" + dubboProperties.getRegistryAddress();
    }
}
