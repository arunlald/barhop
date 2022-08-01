package com.example.barhop;

import com.example.barhop.entities.Customer;
import com.example.barhop.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarHopApplication {


    public static void main(String[] args) {
        SpringApplication.run(BarHopApplication.class, args);
    }

    /*
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(new Customer(null, "Berke", "Ozten", "berkeozten@gmail.com", "temp123"));
            customerRepository.findAll().forEach(p -> {
                System.out.println(p.getFirstName());
            });
        };
    }
     */
}
