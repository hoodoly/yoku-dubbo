package com.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.dubbo.annotation.Provider;
import com.dubbo.command.DubboServiceEvent;
import com.dubbo.properties.DubboProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Desc:
 * Author:
 * Date: 2016/11/14
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnProperty(value = "dubbo.service", havingValue = "true", matchIfMissing = true)
public class ProviderAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProtocolConfig protocolConfig;

    @Autowired
    private RegistryConfig registryConfig;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ProviderConfig providerConfig;

    @PostConstruct
    public void publishService() throws Exception {
        Map<String, Object> provideServiceMap = applicationContext.getBeansWithAnnotation(Provider.class);
        for (Map.Entry<String, Object> entry : provideServiceMap.entrySet()){
            publish(entry.getKey(), entry.getValue());
        }
        applicationContext.publishEvent(new DubboServiceEvent(""));
    }

    public void publish(String beanName, Object bean) throws Exception {
        Class<?> interfaceType = bean.getClass().getInterfaces()[0];
        ServiceBean serviceBean = new ServiceBean();
        serviceBean.setInterface(interfaceType);
        serviceBean.setApplicationContext(applicationContext);
        serviceBean.setApplication(applicationConfig);
        serviceBean.setRegistry(registryConfig);
        serviceBean.setProtocol(protocolConfig);
        serviceBean.setProvider(providerConfig);
        serviceBean.setRef(bean);
        serviceBean.afterPropertiesSet();
        serviceBean.export();
    }
}
