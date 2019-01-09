package com.imyeego.service.impl;

import com.imyeego.mapper.CustomerMapper;
import com.imyeego.pojo.Customer;
import com.imyeego.pojo.PageBean;
import com.imyeego.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageBean findByPage(Customer customer, int pageCode, int pageSize) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerMapper.findById(id);
    }

    @Override
    public Customer findByName(String name) {
        return customerMapper.findByName(name);
    }

    @Override
    public void create(Customer customer) {
        customerMapper.create(customer);
    }

    @Override
    public void delete(Long id) {
        customerMapper.delete(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.update(customer);
    }
}
