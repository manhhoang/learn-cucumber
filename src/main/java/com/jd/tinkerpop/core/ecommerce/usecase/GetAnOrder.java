package com.jd.tinkerpop.core.ecommerce.usecase;

import com.jd.tinkerpop.core.ecommerce.domain.Order;
import com.jd.tinkerpop.core.ecommerce.domain.OrderLine;
import com.jd.tinkerpop.core.ecommerce.domain.OrderLines;
import com.jd.tinkerpop.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Pair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tinkerpop.blueprints.Direction.IN;

@Component
public class GetAnOrder {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetAnOrder(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public Order getOrder(String email, String orderID) {
        Vertex vCustomer = userGraphRepository.getForUser(email);
        return toOrder(vCustomer, orderID);
    }

    private Order toOrder(final Vertex vCustomer, String orderID) {
        Order order = new Order();
        order.setEmail(vCustomer.getProperty("email"));
        List<OrderLine> lines = new ArrayList<>();
        List<DateTime> checkOutTimes = getCheckOutTimes(vCustomer);
        DateTime curCheckOut = checkOutTimes.get(0);
        DateTime preCheckOut = checkOutTimes.get(1);

        GremlinPipeline pipeProduct = new GremlinPipeline();
        pipeProduct.start(vCustomer);
        List<Vertex> vProducts = pipeProduct.outE("visit").inV().outE("add to basket").as("basket").property("createdAt")
                .filter((argument) -> ((DateTime) argument).isAfter(preCheckOut) && ((DateTime) argument).isBefore(curCheckOut))
                .back("basket").inV().toList();
        Set<Vertex> vSetProducts = new HashSet(vProducts);
        for (Vertex product : vSetProducts) {
            OrderLine orderLine = new OrderLine();
            orderLine.setName(product.getProperty("name"));
            orderLine.setImage(product.getProperty("image"));
            orderLine.setPrice(product.getProperty("price"));
            orderLine.setQty(getQty(product, preCheckOut, curCheckOut));
            lines.add(orderLine);
        }
        OrderLines orderLines = new OrderLines();
        orderLines.setOrderID(orderID);
        orderLines.setLines(lines);
        order.setOrder(orderLines);
        return order;
    }

    private int getQty(Vertex product, DateTime preCheckOut, DateTime curCheckOut) {
        List<Edge> eAddToBaskets = (List<Edge>) product.getEdges(IN, "add to basket");
        int qty = 0;
        for (Edge eAdd : eAddToBaskets) {
            DateTime addToBasketTime = eAdd.getProperty("createdAt");
            if ((preCheckOut != null && addToBasketTime.isAfter(preCheckOut) && addToBasketTime.isBefore(curCheckOut))
                    || (preCheckOut == null && addToBasketTime.isBefore(curCheckOut))) {
                qty += (int) eAdd.getProperty("qty");
            }
        }
        return qty;
    }

    private List<DateTime> getCheckOutTimes(final Vertex vCustomer) {
        GremlinPipeline pipe = new GremlinPipeline();
        pipe.start(vCustomer);
        return (List<DateTime>) pipe.outE("visit").inV().outE("check out").order((argument) ->
                ((DateTime) ((Pair<Edge, Edge>) argument).getB().getProperty("createdAt"))
                        .compareTo(((Pair<Edge, Edge>) argument).getA().getProperty("createdAt"))
        ).property("createdAt").toList();
    }
}
