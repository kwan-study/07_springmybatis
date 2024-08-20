package com.ohgiraffers.transactional.section01.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService registOrder;

    private static Stream<Arguments> getOrderInfo() {
        OrderDTO orderInfo = new OrderDTO();
        orderInfo.setOrderDate(LocalDate.now());
        orderInfo.setOrderTime(LocalTime.now());

        orderInfo.setOrderMenus(
                List.of(
                        new OrderMenuDTO(3, 10),
                        new OrderMenuDTO(4, 5),
                        new OrderMenuDTO(10, 20)
                )
        );

        return Stream.of(
                Arguments.of(orderInfo)
        );
    }

    @DisplayName("주문 등록 테스트")
    @ParameterizedTest
    @MethodSource("getOrderInfo")
    void testRegistNewOrder(OrderDTO orderInfo) {
        Assertions.assertDoesNotThrow(
                () -> registOrder.registNewOrder(orderInfo)
        );
    }
}