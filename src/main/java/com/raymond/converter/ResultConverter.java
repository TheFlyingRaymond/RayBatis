package com.raymond.converter;

import java.sql.ResultSet;

public interface ResultConverter {
    public Object convert(ResultSet resultSet);
}
