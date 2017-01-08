package spring;

import org.springframework.stereotype.Component;

/**
 * Desc:
 * Author:
 * Date: 2016/11/19
 */
@Component
public class SpringBean2 {

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
