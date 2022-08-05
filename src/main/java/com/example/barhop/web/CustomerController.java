package com.example.barhop.web;

import com.example.barhop.entities.Customer;
import com.example.barhop.entities.OrderList;
import com.example.barhop.entities.Vendor;
import com.example.barhop.repositories.CustomerRepository;
import com.example.barhop.entities.BarAndDeal;
import com.example.barhop.repositories.BarAndDealRepository;
import com.example.barhop.repositories.OrderListRepository;
import com.example.barhop.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@SessionAttributes({"ActiveCustomer","ActiveVendor","OrderId"})
@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BarAndDealRepository barAndDealRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private OrderListRepository orderListRepository;

    static OrderList tempOrder;
    static List<BarAndDeal> listBarAndDeals;
    static String activeUserMail;


    @GetMapping(path = "/index")
    public String customer() {
        activeUserMail = "";
        return "index";
    }

    @GetMapping(path = "/signUp")
    public String signUp(Model model) {
        model.addAttribute("customer", new Customer());
        return "signUp";
    }

    @GetMapping(path = "/vendorSignUp")
    public String vendorSignUp(Model model) {


        model.addAttribute("vendor", new Vendor());
        return "vendorSignUp";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if(customerRepository.findCustomerByMail(customer.getMail()) != null){
            model.addAttribute("customer", new Customer());
            model.addAttribute("loginMessage", "This mail is already in use.");
            return "signUp";
        }
        if (bindingResult.hasErrors()) {
            return "signUp";
        } else {

            customerRepository.save(customer);
            return "redirect:index";
        }
    }

    @PostMapping(path = "/saveVendor")
    public String saveVendor(Model model, Vendor vendor, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        if(vendorRepository.findVendorByMail(vendor.getMail()) != null){
            model.addAttribute("vendor", new Vendor());
            model.addAttribute("loginMessage", "This mail is already in use.");
            return "vendorSignUp";
        }

        if (bindingResult.hasErrors()) {
            return "vendorSignUp";
        } else {

            vendorRepository.save(vendor);
            return "redirect:index";
        }
    }

    @GetMapping(path = "/login")
    public String customers(Model model,  ModelMap mm, @RequestParam(name = "mail", defaultValue = "") String mail, @RequestParam(name = "password", defaultValue = "") String password, @RequestParam(name = "type", defaultValue = "Customer") String type) {


        if (mail.isEmpty()) {
            model.addAttribute("loginMessage", "Please Enter a Valid Email.");
            return "index";

        } else if (password.isEmpty()){
            model.addAttribute("loginMessage", "Please Enter The Password.");
            return "index";
        }
        else {
            try{
                if (type.equals("customer")){
                    Customer tempCustomer;
                    tempCustomer = customerRepository.findCustomerByMail(mail);
                    if (tempCustomer != null){
                        if (password.equals(tempCustomer.getPassword())){

                            mm.put("ActiveCustomer", tempCustomer);
                            listBarAndDeals = barAndDealRepository.findAll();
                            model.addAttribute("listBarAndDeals", listBarAndDeals);

                            tempOrder = new OrderList();
                            tempOrder.setMail(mail);
                            tempOrder.setBar_list("");
                            tempOrder.setNumber_of_bars(0);
                            tempOrder.setDate(new Date());

                            model.addAttribute("tempOrder", tempOrder);
                            activeUserMail = mail;

                            return "order";
                        }else {
                            model.addAttribute("loginMessage", "The Password You Entered Is Incorrect. Please Try Again.");
                            return "index";
                        }
                    }
                    model.addAttribute("loginMessage", "Your Email Was Incorrect. Please Try Again.");
                    return "index";
                }
                else {
                    Vendor tempVendor;
                    tempVendor = vendorRepository.findVendorByMail(mail);
                    if (tempVendor != null){
                        if (password.equals(tempVendor.getPassword())){

                            mm.put("ActiveVendor", tempVendor);
                            activeUserMail = mail;
                            model.addAttribute("listBarAndDeals", barAndDealRepository.findBarAndDealByBarOwnerMail(activeUserMail));
                            return "vendor_page";
                        }else {
                            model.addAttribute("loginMessage", "The Password You Entered Is Incorrect. Please Try Again.");
                            return "index";
                        }
                    }
                    model.addAttribute("loginMessage", "Your Email Was Incorrect. Please Try Again.");
                    return "index";
                }

            }catch (NoSuchElementException e){
                model.addAttribute("loginMessage", "This User does not exist!");
            }
        }

        return "index";
    }

    @GetMapping(path = "/addBar")
    public String addBar(Model model,  ModelMap mm, @RequestParam(name = "bar") String bar) {


        tempOrder.addBar(bar);
        model.addAttribute("tempOrder", tempOrder);
        model.addAttribute("listBarAndDeals", listBarAndDeals);
        return "order";
    }

    @GetMapping(path = "/removeBar")
    public String removeBar(Model model,  ModelMap mm, @RequestParam(name = "bar") String bar) {
        tempOrder.removeBar(bar);
        model.addAttribute("tempOrder", tempOrder);
        model.addAttribute("listBarAndDeals", listBarAndDeals);
        return "order";
    }

    @GetMapping(path = "/searchCity")
    public String searchCity(Model model,  ModelMap mm, @RequestParam(name = "city", defaultValue = "") String city) {


        if (city.isEmpty()) {
            listBarAndDeals = barAndDealRepository.findAll();
        } else {
            listBarAndDeals = barAndDealRepository.findBarAndDealByCity(city);
        }
        model.addAttribute("listBarAndDeals", listBarAndDeals);
        return "order";
    }

    @GetMapping(path = "/checkout")
    public String checkout(Model model,  ModelMap mm) {

        orderListRepository.save(tempOrder);
        model.addAttribute("tempOrder", tempOrder);
        model.addAttribute("listBarAndDeals", getBar());
        return "ready_order";
    }

    public List<BarAndDeal> getBar (){
        List<BarAndDeal> returnList = new ArrayList<>();
        for (String bar: tempOrder.convertToList()) {
            returnList.add(barAndDealRepository.findBarAndDealByBarName(bar));
        }
        return returnList;
    }

    @GetMapping(path = "/vendorDealUpload")
    public String vendorDealUpload(Model model) {
        model.addAttribute("BarAndDeal", new BarAndDeal());
        return "vendorDealUpload";
    }

    @PostMapping(path = "/saveDeal")
    public String saveDeal(Model model, BarAndDeal barAndDeal, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        barAndDeal.setBarOwnerMail(activeUserMail);
        if (bindingResult.hasErrors()) {
            return "vendorDealUpload";
        } else {

            barAndDealRepository.save(barAndDeal);
            model.addAttribute("listBarAndDeals", barAndDealRepository.findBarAndDealByBarOwnerMail(activeUserMail));
            return "vendor_page";
        }
    }

    @PostMapping(path = "/removeDeal")
    public String removeDeal(Model model, @RequestParam(name = "bar", defaultValue = "") BarAndDeal deal, BindingResult bindingResult, ModelMap mm, HttpSession session) {

        barAndDealRepository.delete(deal);
        model.addAttribute("listBarAndDeals", barAndDealRepository.findBarAndDealByBarOwnerMail(activeUserMail));
        return "vendor_page";

    }

}
