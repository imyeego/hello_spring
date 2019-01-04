package com.imyeego.mapper;

import com.imyeego.pojo.User;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserMapper {

    User login(String username);

    User findById(Long id);
}
