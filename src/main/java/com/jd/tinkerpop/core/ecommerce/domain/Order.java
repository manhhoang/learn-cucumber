package com.jd.tinkerpop.core.ecommerce.domain;

public class Order {

    public String orderID;

    public String email = null;

    public OrderLines order;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrderLines getOrder() {
        return order;
    }

    public void setOrder(OrderLines order) {
        this.order = order;
    }
}
