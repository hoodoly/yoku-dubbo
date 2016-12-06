package com.dubbo.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Service
public @interface Provider {

}
