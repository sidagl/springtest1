package com.springtests.yigit.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alien {
    // this allows autowiring of property to controlled component
    // and allows usage without init
    @Autowired
    private Computer computer;

    private int age = 15;

    public Alien(int age) {
        this.age = age;
        System.out.println("Alien obj created");
    }

    public void code() {
        System.out.println("Test1");
    }

    public void probe() {
        System.out.println("You've been probed");
    }

    public void control() {
        computer.compile();
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}
