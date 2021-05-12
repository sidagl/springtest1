package com.springtests.yigit.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alien {

    // this allows autowiring of property to controlled component and allows usage without init
    @Autowired
    Laptop laptop;

    public void code() {
        System.out.println("Test1");
    }

    public void probe() {

    }

    public void control() {
        laptop.compile();
    }
}
