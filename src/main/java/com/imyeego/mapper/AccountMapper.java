package com.imyeego.mapper;

import org.apache.ibatis.annotations.Param;

public interface AccountMapper {

    void set(@Param("id") int id, @Param("money") double money);
    double get(@Param("id") int id);
}
