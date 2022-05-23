package com.sparta.week_iv_homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WeekIvHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeekIvHomeworkApplication.class, args);
    }

}
