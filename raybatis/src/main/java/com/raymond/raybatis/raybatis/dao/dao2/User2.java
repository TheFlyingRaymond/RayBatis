package com.raymond.raybatis.raybatis.dao.dao2;

import com.raymond.raybatis.raybatis.annotation.RayAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RayAlias("user2Alias")
public class User2 {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
