package spring;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/19
 */

@Configuration
public class SpringBeanConfig {

    private final static Logger log = LoggerFactory.getLogger(SpringBeanConfig.class);

    @Bean
    public SpringBean springBean(){
        return new SpringBean();
    }

    @Bean
    public BeanPostProcessor beanPostProcessor(){
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
                System.out.println("====bean 后置处理器=====");
                return o;
            }

            @Override
            public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
                return o;
            }
        };
    }

    @Bean
    public EventListener eventListener(){
        return new EventListener(){
            @Subscribe
            public void OnListerner(){
                log.info("onListener");
            }

        };
    }


}
