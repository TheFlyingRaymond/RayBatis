package com.raymond.raybatis.raybatis.mapper;

import java.util.List;

import com.raymond.raybatis.raybatis.dao.User;

public interface UserMapper {
    List<User> selectAll();

    User selectMax();

    Long count();
}
