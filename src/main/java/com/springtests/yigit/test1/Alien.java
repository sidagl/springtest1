package com.springtests.yigit.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alien {


    private int age=15;

    // this allows autowiring of property to controlled component and allows usage without init
    @Autowired
    private Laptop laptop;

    public Alien(){
        System.out.println("Alien obj created");
    }

    public void code() {
        System.out.println("Test1");
    }

    public void probe() {
        System.out.println("You've been probed");
    }

    public void control() {
        laptop.compile();
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}
