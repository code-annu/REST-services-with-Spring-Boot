package com.example.rest_services.utils;

import com.example.rest_services.models.Employee;
import com.example.rest_services.repositories.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @Bean
    CommandLineRunner initDatabase(EmployeeRepo employeeRepo){
        return args -> {
            log.info("Preloading: "+employeeRepo.save(new Employee("Shivam Pandey","Front Developer")));
            log.info("Preloading: "+employeeRepo.save(new Employee("Anshuman Singh","Backend Developer")));
            log.info("Preloading: "+employeeRepo.save(new Employee("Anurag Rajput","CEO")));
        };
    }
}
