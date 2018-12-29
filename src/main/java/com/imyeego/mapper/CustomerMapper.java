package com.imyeego.mapper;

import com.github.pagehelper.Page;
import com.imyeego.pojo.Customer;

import java.util.List;

public interface CustomerMapper {
    void create(Customer customer);

    void delete(Long id);

    Customer findById(Long id);

    Customer findByName(String name);

    void update(Customer customer);

    List<Customer> findAll();

    Page<Customer> findByPage(Customer customer);
}
