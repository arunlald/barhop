package com.example.barhop.web;

import com.example.barhop.entities.Customer;
import com.example.barhop.entities.Vendor;
import com.example.barhop.repositories.CustomerRepository;
import com.example.barhop.entities.BarAndDeal;
import com.example.barhop.repositories.BarAndDealRepository;
import com.example.barhop.repositories.VendorRepository;
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

@SessionAttributes({"ActiveCustomer","ActiveVendor"})
@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BarAndDealRepository barAndDealRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping(path = "/index")
    public String customer() {
        return "index";
    }

    @GetMapping(path = "/signUp")
    public String signUp(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerSignUp";
    }

    @GetMapping(path = "/vendorSignUp")
    public String vendorSignUp(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "vendorSignUp";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "customerSignUp";
        } else {

            customerRepository.save(customer);
            return "redirect:index";
        }
    }

    @PostMapping(path = "/saveVendor")
    public String saveVendor(Model model, Vendor vendor, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "vendorSignUp";
        } else {

            vendorRepository.save(vendor);
            return "redirect:index";
        }
    }

    @PostMapping(path = "/newDeal")
    public String uploadDeal(Model model, Vendor vendor, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "vendorDealUpload";
        } else {

            vendorRepository.save(vendor);
            return "redirect:index";
        }
    }

    @GetMapping(path = "/login")
    public String customers(Model model,  ModelMap mm, @RequestParam(name = "mail", defaultValue = "") String mail, @RequestParam(name = "password", defaultValue = "") String password, @RequestParam(name = "type", defaultValue = "Customer") String type) {


        if (mail.isEmpty()) {
            // mail area is empty
        } else if (password.isEmpty()){
            // password area is empty
        }
        else {
            try{
                if (type.equals("customer")){
                    Customer tempCustomer;
                    tempCustomer = customerRepository.findCustomerByMail(mail);
                    if (tempCustomer != null){
                        if (password.equals(tempCustomer.getPassword())){
                            mm.put("ActiveCustomer", tempCustomer);
                            List<BarAndDeal> listBarAndDeals = barAndDealRepository.findAll();
                            model.addAttribute("listBarAndDeals", listBarAndDeals);
                            return "order";
                        }else {
                            // Password not correct
                        }
                    }
                    // Mail doesnt exist
                }
                else {
                    Vendor tempVendor;
                    tempVendor = vendorRepository.findVendorByMail(mail);
                    if (tempVendor != null){
                        if (password.equals(tempVendor.getPassword())){
                            mm.put("ActiveVendor", tempVendor);
                            List<BarAndDeal> listBarAndDeals = barAndDealRepository.findAll();
                            model.addAttribute("listBarAndDeals", listBarAndDeals);
                            return "vendor_page";
                        }else {
                            // Password not correct
                        }
                    }
                    // Mail doesnt exist
                }

            }catch (NoSuchElementException e){
                // mail not correct or not in database
            }
        }

        return "index";
    }

    @GetMapping(path = "/searchCity")
    public String searchCity(Model model,  ModelMap mm, @RequestParam(name = "city", defaultValue = "") String city) {

        List<BarAndDeal> barAndDealList;
        if (city.isEmpty()) {
            barAndDealList = barAndDealRepository.findAll();
        } else {
            barAndDealList = barAndDealRepository.findBarAndDealByCity(city);
        }
        model.addAttribute("listBarAndDeals", barAndDealList);
        return "order";
    }

}
