package com.example.barhop.web;

import com.example.barhop.entities.Customer;
import com.example.barhop.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/index")
    public String customer() {
        return "index";
    }

    @GetMapping(path = "/signUp")
    public String signUp(Model model) {
        model.addAttribute("customer", new Customer());
        return "signUp";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "signUp";
        } else {

            customerRepository.save(customer);
            return "redirect:index";
        }
    }
}
