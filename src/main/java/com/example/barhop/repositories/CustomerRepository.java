package com.example.barhop.repositories;

import com.example.barhop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    List<Customer> findCustomerById (long kw);
    Customer findCustomerByMail(String kn);
}
