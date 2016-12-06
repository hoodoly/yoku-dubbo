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

    @Override
    public void run(String... args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}