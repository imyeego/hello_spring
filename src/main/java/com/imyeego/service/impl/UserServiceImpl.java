package com.imyeego.service.impl;

import com.imyeego.mapper.UserMapper;
import com.imyeego.pojo.User;
import com.imyeego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String name){
        return userMapper.login(name);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(User user) {

    }
}
