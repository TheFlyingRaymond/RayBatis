package com.raymond.raybatis.raybatis.dao.dao2;

import com.raymond.raybatis.raybatis.annotation.RayAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RayAlias()
public class Country2 {
    private Long id;
    private String countryName;
    private String countryCode;
}
