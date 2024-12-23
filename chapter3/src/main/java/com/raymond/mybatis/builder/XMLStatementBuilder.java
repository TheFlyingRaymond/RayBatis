package com.raymond.mybatis.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.parsing.XNode;

import com.raymond.mybatis.mapping.SqlCommandType;
import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.session.Configuration;

public class XMLStatementBuilder extends BaseBuilder {
    private MapperBuilderAssistant builderAssistant;
    private XNode context;

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.context = context;
    }

    public void parse() {
        String id = context.getStringAttribute("id");

        // 读取节点名
        String nodeName = context.getNode().getNodeName();
        // 读取和判断语句类型
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        String keyStatementId = builderAssistant.applyCurrentNamespace(id);

        SimpleSqlSource sqlSource = createSimpleSqlSource();

        builderAssistant.addMapperStatement(keyStatementId, sqlCommandType, sqlSource);
    }

    private SimpleSqlSource createSimpleSqlSource() {
        ParsedSql parsedSql = parseSql(context.getStringBody());
        return new SimpleSqlSource(configuration, parsedSql.getSql(), parsedSql.getParameterNames());
    }


    public static class ParsedSql {
        private String sql;
        private List<String> parameterNames;

        public ParsedSql(String sql, List<String> parameterNames) {
            this.sql = sql;
            this.parameterNames = parameterNames;
        }

        public String getSql() {
            return sql;
        }

        public List<String> getParameterNames() {
            return parameterNames;
        }
    }

    private ParsedSql parseSql(String sql) {
        StringBuilder parsedSql = new StringBuilder();
        List<String> parameterNames = new ArrayList<>();

        char[] src = sql.toCharArray();
        int length = src.length;
        int offset = 0;

        while (offset < length) {
            if (src[offset] == '#' && offset + 1 < length && src[offset + 1] == '{') {
                int start = offset + 2;
                int end = findClosingBrace(src, start);
                if (end == -1) {
                    throw new IllegalArgumentException("Unclosed placeholder in SQL: " + sql);
                }
                String parameterName = new String(src, start, end - start).trim();
                parameterNames.add(parameterName);
                parsedSql.append('?');
                offset = end + 1;
            } else {
                parsedSql.append(src[offset]);
                offset++;
            }
        }

        return new ParsedSql(parsedSql.toString(), parameterNames);
    }

    private int findClosingBrace(char[] src, int start) {
        for (int i = start; i < src.length; i++) {
            if (src[i] == '}') {
                return i;
            }
        }
        return -1;
    }
}
