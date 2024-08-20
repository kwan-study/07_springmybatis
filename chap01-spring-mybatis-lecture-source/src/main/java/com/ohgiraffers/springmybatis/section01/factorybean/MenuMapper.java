package com.ohgiraffers.springmybatis.section01.factorybean;

import java.util.List;

public interface MenuMapper {

    List<MenuDTO> findAllMenusByOrderableStatus(String orderableStatus);
}
