package com.dubbo.resolver;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.dubbo.annotation.Consumer;
import org.springframework.context.ApplicationContext;

/**
 * Desc:
 * Author:
 * Date: 2016/11/14
 */
public class DubboConsumerResolver implements ConsumerResolver{

    private RegistryConfig registryConfig;

    private ApplicationConfig applicationConfig;

    private ApplicationContext applicationContext;

    private ConsumerConfig consumerConfig;

    private DubboConsumerResolver(){}

    public DubboConsumerResolver(RegistryConfig registryConfig, ConsumerConfig consumerConfig,
                                 ApplicationConfig applicationConfig, ApplicationContext applicationContext){

        this.applicationConfig = applicationConfig;
        this.applicationContext = applicationContext;
        this.registryConfig = registryConfig;
        this.consumerConfig = consumerConfig;
    }


    @Override
    public <T> T resolve(Class<T> interfaceClazz, Consumer consumer) throws Exception {

        ReferenceBean consumerBean = getComsumerBean(interfaceClazz, consumer);
        //调用dubbo afterPropertiesSet方法获取对应的Bean
        consumerBean.afterPropertiesSet();
        return (T) consumerBean.getObject();
    }

    private <T> ReferenceBean<T> getComsumerBean(Class<T> interfaceClazz, Consumer consumer){

        ReferenceBean<T> consumerBean = new ReferenceBean<>();
        consumerBean.setId(interfaceClazz.getCanonicalName());
        consumerBean.setInterface(interfaceClazz);
        consumerBean.setApplicationContext(applicationContext);
        consumerBean.setApplication(applicationConfig);
        consumerBean.setRegistry(registryConfig);
        consumerBean.setConsumer(consumerConfig);
        return consumerBean;
    }

}
