package com.ohgiraffers.transactional.section01.annotation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/* 설명. 테이블에 꼭 일치하지 않더라도 주문을 위해 사용자가 넘겨준 값을 담을 수 있는 DTO 클래스 */
/* 설명. DTO(Data Transfer Object): 계층을 넘어다니며 값을 옮겨주는 클래스 */
public class OrderDTO {
    private LocalDate orderDate;
    private LocalTime orderTime;
    private List<OrderMenuDTO> orderMenus;

    public OrderDTO() {
    }

    public OrderDTO(LocalDate orderDate, LocalTime orderTime, List<OrderMenuDTO> orderMenus) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderMenus = orderMenus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderMenuDTO> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<OrderMenuDTO> orderMenus) {
        this.orderMenus = orderMenus;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", orderMenus=" + orderMenus +
                '}';
    }
}
