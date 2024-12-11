package com.raymond.parsing;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLPathParser {
    private  Document doc;
    private  DocumentBuilderFactory factory;
    public XMLPathParser(String filePath) {
        try {
            this.factory = DocumentBuilderFactory.newInstance();
            this.doc = factory.newDocumentBuilder().parse(filePath);
        } catch (Exception e) {
            log.error("解析xml文件失败", e);
        }
        log.info("解析xml文件成功");
    }
}
