package com.fzz.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fzz")
public class NewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class,args);
    }
}
