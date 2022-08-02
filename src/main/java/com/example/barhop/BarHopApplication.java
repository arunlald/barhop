package com.example.barhop;

import com.example.barhop.entities.BarAndDeal;
import com.example.barhop.entities.Customer;
import com.example.barhop.repositories.BarAndDealRepository;
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

/*
    @Bean
    CommandLineRunner commandLineRunner(BarAndDealRepository barAndDealRepository) {
        return args -> {
            barAndDealRepository.save(new BarAndDeal(null, "Bar1", "10 percent off", "New Westminister", "somewhere around NW"));
            barAndDealRepository.save(new BarAndDeal(null, "Bar2", "11 percent off", "New Westminister", "somewhere around NW2"));
            barAndDealRepository.save(new BarAndDeal(null, "Bar3", "10 percent off", "Burnaby", "somewhere around Burnaby"));
        };
    }
*/
}
