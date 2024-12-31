package com.raymond.mybatis.testdata;


import com.raymond.mybatis.annotation.Param;
import com.raymond.mybatis.testdata.dao.Country;
import com.raymond.mybatis.testdata.dao.People;

public interface PeopleMapper {
    People selectById(@Param("id") Long id);
}
