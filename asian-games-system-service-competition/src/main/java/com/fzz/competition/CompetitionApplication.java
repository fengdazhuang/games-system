package com.fzz.competition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fzz")
public class CompetitionApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompetitionApplication.class,args);
    }
}
