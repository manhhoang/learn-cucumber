package com.jd.tinkerpop.core.ecommerce.domain;

import java.util.ArrayList;
import java.util.List;

public class BasketDetail {

    public String email;

    public List<OrderLine> basket = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderLine> getBasket() {
        return basket;
    }

    public void setBasket(List<OrderLine> basket) {
        this.basket = basket;
    }
}
