package com.dubbo.command;

import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/11/14
 */
public class DubboServiceLatchCommandLineRunner implements CommandLineRunner {
    public DubboServiceLatchCommandLineRunner() {}

    //CommandLineRunner和ApplicationRunner在spring容器启动完成之后执行(可用于加载配置文件等)
    // 多个Runner时在实现类上加上@Order注解添加顺序。@Order(value=整数值)
    @Override
    public void run(String... args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}