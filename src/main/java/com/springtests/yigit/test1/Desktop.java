package com.springtests.yigit.test1;

import org.springframework.stereotype.Component;

@Component
public class Desktop  implements Computer{
    public void compile() {
        System.out.println("compiled in desktop");
    }
}
