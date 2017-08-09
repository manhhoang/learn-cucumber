package com.intilery.exercise.core.ecommerce.usecase;

import com.intilery.exercise.core.ecommerce.domain.Order;
import com.intilery.exercise.core.ecommerce.domain.OrderDetail;
import com.intilery.exercise.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.util.iterators.SingleIterator;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
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
        Vertex vCustomer = userGraphRepository.getForUser(email);
        return toOrderDetailsGemlin(vCustomer);
    }

    private OrderDetail toOrderDetailsGemlin(final Vertex vCustomer) {
        GremlinPipeline pipe = new GremlinPipeline();
        pipe.start(vCustomer);
        Pipe ab = pipe.outV().select();
        Iterator adf = ab.iterator();
        while(adf.hasNext()) {
            Vertex v = (Vertex)adf.next();
        }
        List<Vertex> vertices = pipe.outV().select().toList();
        for(Vertex vertex: vertices) {
            if(vertex.getProperty("type").equals("order")) {

            }
        }
        List<Edge> edges = pipe.toList();

        return null;
    }

    private OrderDetail toOrderDetails(final Vertex vCustomer) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setEmail(vCustomer.getProperty("email"));
        List<Order> orders = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(vCustomer);
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
