package com.intilery.exercise.core.ecommerce.usecase;

import com.intilery.exercise.core.ecommerce.domain.Order;
import com.intilery.exercise.core.ecommerce.domain.OrderDetail;
import com.intilery.exercise.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;

@Component
public class GetOrderDetails {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetOrderDetails(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public OrderDetail getOrderDetails(String email) throws IOException, JSONException {
        Vertex customer = userGraphRepository.getForUser(email);
        return toOrderDetails(customer);
    }

    private OrderDetail toOrderDetails(final Vertex customer) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setEmail(customer.getProperty("email"));
        List<Order> orders = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(customer);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            List<Edge> edges = (List<Edge>) v.getEdges(OUT);
            if (!edges.isEmpty()) {
                for (final Edge outEdge : edges) {
                    Vertex ver = outEdge.getVertex(IN);
                    if (ver.getProperty("type").equals("order")) {
                        Order order = new Order();
                        order.setOrderID(ver.getProperty("orderID"));
                        orders.add(order);
                    }
                    queue.add(outEdge.getVertex(IN));
                }
            }
        }
        orderDetail.setOrders(orders);
        return orderDetail;
    }
}
