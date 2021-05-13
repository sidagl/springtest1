package com.springtests.yigit.test1;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class Test1Application {

    public static void main(String[] args) {
        //Getting bean from app context
        ApplicationContext appContext = SpringApplication.run(Test1Application.class, args);
        //inject Alien class using @Component annotation -
        // not responsible for object lifecycle. DI
        Alien obj = appContext.getBean(Alien.class);
        obj.code();
        obj.control();

        System.out.println("*******");


        //Getting bean from BeanFactory
        // -- depends on spring xml config
        BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
        Alien alien = (Alien) beanFactory.getBean("alien");
        alien.code();
    }
}
