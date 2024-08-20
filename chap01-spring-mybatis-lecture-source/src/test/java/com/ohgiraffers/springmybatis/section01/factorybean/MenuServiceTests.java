package com.ohgiraffers.springmybatis.section01.factorybean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTests {

    @Autowired
    private MenuService menuService;

    @DisplayName("주문가능 상태별 메뉴 조회 확인 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"Y", "N"})
    void testFindAllMenus(String orderableStatus) {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<MenuDTO> menus = menuService.findAllMenusByOrderableStatus(orderableStatus);
                    menus.forEach(System.out::println);
                }
        );
    }

}