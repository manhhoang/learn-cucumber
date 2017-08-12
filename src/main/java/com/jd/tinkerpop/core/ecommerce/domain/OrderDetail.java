package com.jd.tinkerpop.core.ecommerce.domain;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {

    public String email;

    public List<Order> orders = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
