package com.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.dubbo.annotation.Consumer;
import com.dubbo.resolver.ConsumerResolver;
import com.dubbo.resolver.DubboConsumerResolver;
import com.dubbo.resolver.SpringConsumerResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableAutoConfiguration
public class ConsumerAutoCofiguration {

    @Autowired
    private ConsumerResolver consumerResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "consumer-bean-processor")
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
                Class<?> objClz = bean.getClass();
                if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
                    objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
                }

                ReflectionUtils.doWithFields(objClz, new ReflectionUtils.FieldCallback() {
                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                        Consumer consumer = field.getAnnotation(Consumer.class);
                        if (consumer != null) {
                            Class type = field.getType();

                            //try to resolver bean local first
                            Object object = null;
                            try {
                                object = applicationContext.getBean(type);
                            }catch (Exception e){
                                //
                            }
                            if(object== null) {
                                try {
                                    object = consumerResolver.resolve(type, consumer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                field.setAccessible(true);
                                field.set(bean, object);
                                field.setAccessible(false);
                            } catch (Exception e) {
                                throw new BeanCreationException(beanName, e);
                            }

                        }
                    }
                });
                return bean;
            }

            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

    @Bean
    @ConditionalOnProperty(value = "dubbo.module", havingValue = "true", matchIfMissing = true)
    public ConsumerResolver springConsumerResolver(ApplicationContext applicationContext) {
        return new SpringConsumerResolver(applicationContext);
    }

    @Bean
    @ConditionalOnProperty(value = "dubbo.module", havingValue = "false")
    public DubboConsumerResolver dubboConsumerResolver(ApplicationContext applicationContext,
                                                       ApplicationConfig applicationConfig,
                                                       RegistryConfig registryConfig,
                                                       ConsumerConfig consumerConfig) {
        return new DubboConsumerResolver(registryConfig, consumerConfig, applicationConfig, applicationContext);
    }
}
