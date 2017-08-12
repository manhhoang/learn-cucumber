package com.jd.tinkerpop.core.ecommerce.usecase;

import com.jd.tinkerpop.core.ecommerce.domain.Order;
import com.jd.tinkerpop.core.ecommerce.domain.OrderDetail;
import com.jd.tinkerpop.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetOrders {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetOrders(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public OrderDetail getOrders(String email) {
        Vertex vCustomer = userGraphRepository.getForUser(email);
        return toOrders(vCustomer);
    }

    private OrderDetail toOrders(final Vertex vCustomer) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setEmail(vCustomer.getProperty("email"));
        List<Order> orders = new ArrayList<>();

        GremlinPipeline pipe = new GremlinPipeline();
        pipe.start(vCustomer);
        List<Vertex> vOrders = pipe.outE("visit").inV().outE("check out").inV().toList();
        for(Vertex vOrder: vOrders) {
            Order order = new Order();
            order.setOrderID(vOrder.getProperty("orderID"));
            orders.add(order);
        }
        orderDetail.setOrders(orders);
        return orderDetail;
    }
}
