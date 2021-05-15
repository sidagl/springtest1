package com.springtests.yigit.test1;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class Test1Application {

    public static void main(String[] args) {
        //Getting bean from app context
        //ApplicationContext appContext = SpringApplication.run(Test1Application.class, args);
        //inject Alien class using @Component annotation -
        // not responsible for object lifecycle. DI
        //Alien obj = appContext.getBean(Alien.class);
        //obj.code();
        //obj.control();

        //System.out.println("*******");


        //Getting bean from BeanFactory
        // -- depends on spring.xml config
        // Spring Container returns only 1 object by default when you add it to spring xml
        // BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("spring.xml")); //deprecated
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("spring.xml");

        Alien alien = (Alien) beanFactory.getBean("alien");
        alien.setAge(4234);
        alien.code();
        alien.control();
        System.out.println("alien age:" + alien.getAge());


        Alien alien2 = (Alien) beanFactory.getBean("alien");
        alien2.code();
        System.out.println("alien age:" + alien2.getAge());
    }
}
