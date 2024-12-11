package com.raymond.mapper;

import java.util.List;

import com.raymond.dao.User;

public interface UserMapper {
    List<User> selectAll();

    User selectMax();

    Long count();
}
