package com.raymond.mybatis.session;

public class MockSqlSessionFactory implements SqlSessionFactory {
    @Override
    public SqlSession openSession() {
        return new MockSqlSession(new Configuration());
    }
}
