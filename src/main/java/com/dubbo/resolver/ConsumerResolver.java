package com.dubbo.resolver;

import com.dubbo.annotation.Consumer;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
public interface ConsumerResolver {

    <T> T resolve(Class<T> interfaceType, Consumer consumer) throws Exception;
}
