package com.dubbo.resolver;

import com.dubbo.annotation.Consumer;

/**
 * Desc:
 * Author:
 * Date: 2016/11/14
 */
public interface ConsumerResolver {

    <T> T resolve(Class<T> interfaceType, Consumer consumer) throws Exception;
}
