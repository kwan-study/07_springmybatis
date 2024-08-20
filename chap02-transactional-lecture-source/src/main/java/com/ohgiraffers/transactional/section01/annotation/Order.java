package com.ohgiraffers.transactional.section01.annotation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private int orderCode;
    private String orderDate;
    private String orderTime;
    private int totalOrderPrice;

    public Order() {
    }

    public Order(LocalDate orderDate, LocalTime orderTime, int totalOrderPrice) {

        /* 설명. LocalDate 또는 LocalTime 형을 DB에 맞춰서 저장하기 위한 변환작업 */
        this.orderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.orderTime = orderTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.totalOrderPrice = totalOrderPrice;
    }

    public Order(int orderCode, String orderDate, String orderTime, int totalOrderPrice) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalOrderPrice = totalOrderPrice;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderCode=" + orderCode +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", totalOrderPrice=" + totalOrderPrice +
                '}';
    }
}
