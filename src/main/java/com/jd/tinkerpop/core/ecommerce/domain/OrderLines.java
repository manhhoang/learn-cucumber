package com.jd.tinkerpop.core.ecommerce.domain;

import java.util.ArrayList;
import java.util.List;

public class OrderLines {

    public String orderID;

    public List<OrderLine> lines = new ArrayList<>();

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }
}
