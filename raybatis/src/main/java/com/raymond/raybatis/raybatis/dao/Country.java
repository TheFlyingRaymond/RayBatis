package com.raymond.raybatis.raybatis.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Long id;
    private String countryName;
    private String countryCode;
}
