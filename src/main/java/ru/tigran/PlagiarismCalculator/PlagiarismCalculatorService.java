package ru.tigran.PlagiarismCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlagiarismCalculatorService {
    public static void main(String[] args) {
        SpringApplication.run(PlagiarismCalculatorService.class, args);
    }
}
