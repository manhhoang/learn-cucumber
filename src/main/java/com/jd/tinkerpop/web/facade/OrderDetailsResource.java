package com.jd.tinkerpop.web.facade;

import com.jd.tinkerpop.core.ecommerce.domain.Order;
import com.jd.tinkerpop.core.ecommerce.domain.OrderDetail;
import com.jd.tinkerpop.core.ecommerce.usecase.GetAnOrder;
import com.jd.tinkerpop.core.ecommerce.usecase.GetOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderDetailsResource {

    private final GetOrders getOrders;

    private final GetAnOrder getAnOrder;

    @Autowired
    public OrderDetailsResource(GetOrders getOrders, GetAnOrder getAnOrder) {
        this.getOrders = getOrders;
        this.getAnOrder = getAnOrder;
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetail getOrders(@PathVariable("email") String email) {
        return getOrders.getOrders(email);
    }

    @RequestMapping(value = "/{email}/{orderID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrder(@PathVariable("email") String email, @PathVariable("orderID") String orderID) {
        return getAnOrder.getOrder(email, orderID);
    }
}
