package com.raymond.parsing;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLPathParserTest {

    @Test
    public void test1(){
        new XMLPathParser("/Users/suncheng11/github/RayBatis/episode3/src/test/resources/batis-config.xml");
        log.info("teswt");
    }
}
