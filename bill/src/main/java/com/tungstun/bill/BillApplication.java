package com.tungstun.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan("com.tungstun")
@EnableKafka
@EnableEurekaClient
public class BillApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillApplication.class, args);
    }
}
