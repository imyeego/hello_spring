package com.imyeego.service;

import com.imyeego.pojo.User;

public interface UserService extends BaseService<User> {

    User login(String name);
}
