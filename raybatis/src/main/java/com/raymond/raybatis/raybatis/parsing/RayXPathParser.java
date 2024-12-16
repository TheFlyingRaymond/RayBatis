package com.raymond.raybatis.raybatis.parsing;

import java.io.Reader;

import org.apache.ibatis.parsing.XPathParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXPathParser extends XPathParser {
    public RayXPathParser(Reader reader) {
        super(reader);
    }
}
