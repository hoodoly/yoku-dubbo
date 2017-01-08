package com.dubbo.resolver;

import com.dubbo.annotation.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Desc:
 * Author:
 * Date: 2016/11/14
 */

public class SpringConsumerResolver implements ConsumerResolver {

    private final Logger log = LoggerFactory.getLogger(SpringConsumerResolver.class);

    private final ApplicationContext applicationContext;

    public SpringConsumerResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T resolve(Class<T> interfaceClazz, Consumer consumer) throws BeansException {
        boolean check = true;
        try {
            return applicationContext.getBean(interfaceClazz);
        }catch (Exception e){
            log.warn("failed to resolve bean for type :{}", interfaceClazz);
            if(check){
                throw e;
            }
            return null;
        }
    }
}
