package com.raymond.converter;

import java.sql.ResultSet;

public interface ResultConverter<T> {
    public T convert(ResultSet resultSet);
}
