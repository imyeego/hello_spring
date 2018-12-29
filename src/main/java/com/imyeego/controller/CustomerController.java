package com.imyeego.controller;


import com.imyeego.pojo.Customer;
import com.imyeego.pojo.UserId;
import com.imyeego.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "/customers")
    public List<Customer> findAll(){
//        if (customerService.findAll().size() == 0){
//            return new ArrayList<Customer>();
//        }
        return customerService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/customer_id", method = RequestMethod.POST)
    public Customer findById(@RequestBody UserId userId){
        return customerService.findById(userId.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/customer_name", method = RequestMethod.POST)
    public Customer findByName(@RequestParam("id") String id, @RequestParam("name") String name){
        return customerService.findByName(name);

    }
}
