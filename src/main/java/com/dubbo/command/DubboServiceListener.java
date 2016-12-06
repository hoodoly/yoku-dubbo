package com.dubbo.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
public class DubboServiceListener implements ApplicationListener, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(DubboServiceListener.class);

    protected ApplicationContext ctx;

    private final String appName;

    public DubboServiceListener(String appName) {
        this.appName = appName;
    }


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof DubboServiceEvent) {
            // now you can do applicationContext.getBean(...)
            // ...
            if (appName != null) {
                log.info("{} boot successfully", appName);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    public String getAppName() {
        return appName;
    }
}

