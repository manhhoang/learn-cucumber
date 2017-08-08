package com.intilery.exercise.core.ecommerce.usecase;

import com.intilery.exercise.core.ecommerce.domain.Order;
import com.intilery.exercise.core.ecommerce.domain.OrderDetail;
import com.intilery.exercise.core.ecommerce.domain.OrderLine;
import com.intilery.exercise.core.ecommerce.domain.OrderLines;
import com.intilery.exercise.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;

@Component
public class GetOrderDetails {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetOrderDetails(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public OrderDetail getOrderDetails(String email) {
        Vertex customer = userGraphRepository.getForUser(email);
        return toOrderDetails(customer);
    }

    public Order getOrderByID(String email, String orderID) {
        Vertex customer = userGraphRepository.getForUser(email);
        return toOrder(customer, orderID);
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

    private Order toOrder(final Vertex customer, String orderID) {
        Order order = new Order();
        order.setEmail(customer.getProperty("email"));
        List<OrderLine> lines = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(customer);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            List<Edge> edges = (List<Edge>) v.getEdges(OUT);
            if (!edges.isEmpty()) {
                for (final Edge outEdge : edges) {
                    Vertex ver = outEdge.getVertex(IN);
                    if (ver.getProperty("type").equals("order") && ver.getProperty("orderID").equals(orderID)) {
                        List<Edge> checkouts = (List<Edge>) ver.getEdges(IN);
                        Set<Edge> products = (HashSet)((HashMap)checkouts.get(0).getVertex(OUT).getEdges(OUT)).get("add to basket");
                        for (Edge e : products) {
                            int qty = e.getProperty("qty") != null ? e.getProperty("qty") : 0;
                            Vertex p = e.getVertex(IN);
                            String image = p.getProperty("image");
                            double price = p.getProperty("price");
                            String name = p.getProperty("name");
                            OrderLine orderLine = new OrderLine();
                            orderLine.setName(name);
                            orderLine.setImage(image);
                            orderLine.setPrice(price);
                            orderLine.setQty(qty);
                            lines.add(orderLine);
                        }
                    }
                    queue.add(outEdge.getVertex(IN));
                }
            }
        }
        OrderLines orderLines = new OrderLines();
        orderLines.setOrderID(orderID);
        orderLines.setLines(lines);
        order.setOrder(orderLines);
        return order;
    }
}
