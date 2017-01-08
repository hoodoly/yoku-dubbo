package com.dubbo.command;

import org.springframework.context.ApplicationEvent;

/**
 * Desc:
 * Author:
 * Date: 2016/11/14
 */
public class DubboServiceEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DubboServiceEvent(Object source) {
        super(source);
    }
}
