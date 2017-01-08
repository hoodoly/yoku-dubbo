package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 * Author:
 * Date: 2016/11/19
 */

@SpringBootApplication
public class SpringMain {

    @Autowired
    SpringBean2 springBean2;

    @Autowired
    SpringBean3 springBean3;

    public static void main(String[] arg) throws InterruptedException {
        SpringApplication.run(SpringMain.class);
        System.out.print("start");
        Thread.sleep(10000L);
    }


}
