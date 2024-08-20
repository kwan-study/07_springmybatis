package com.ohgiraffers.springmybatis.section01.factorybean;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final SqlSessionTemplate sqlSession;

    @Autowired
    public MenuService(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<MenuDTO> findAllMenusByOrderableStatus(String orderableStatus) {
        return sqlSession.getMapper(MenuMapper.class).findAllMenusByOrderableStatus(orderableStatus);
    }
}
