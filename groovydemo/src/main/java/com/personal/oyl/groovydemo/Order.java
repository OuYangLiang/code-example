package com.personal.oyl.groovydemo;

/**
 * @author OuYang Liang
 * @since 2019-05-29
 */
public class Order {
    private int orderId;
    private String orderNo;

    public Order() {
    }

    public Order(int orderId, String orderNo) {
        this.orderId = orderId;
        this.orderNo = orderNo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
