package com.example.barhop.web;

import com.example.barhop.entities.Customer;
import com.example.barhop.repositories.CustomerRepository;
import com.example.barhop.entities.BarAndDeal;
import com.example.barhop.repositories.BarAndDealRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@SessionAttributes({"ActiveCustomer"})
@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BarAndDealRepository barAndDealRepository;

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

    @GetMapping(path = "/login")
    public String customers(Model model,  ModelMap mm, @RequestParam(name = "mail", defaultValue = "") String mail, @RequestParam(name = "password", defaultValue = "") String password) {

        Customer tempCustomer;
        if (mail.isEmpty()) {
            // mail area is empty
        } else if (password.isEmpty()){
            // password area is empty
        }
        else {
            try{
                tempCustomer = customerRepository.findCustomerByMail(mail);
                if (password.equals(tempCustomer.getPassword())){
                    mm.put("ActiveCustomer", tempCustomer);
                    List<BarAndDeal> listBarAndDeals = barAndDealRepository.findAll();
                    model.addAttribute("listBarAndDeals", listBarAndDeals);
                    return "order";
                }else {
                    // Password not correct
                }
            }catch (NoSuchElementException e){
                // mail not correct or not in database
            }
        }

        return "index";
    }
}
