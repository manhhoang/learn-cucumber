package com.intilery.exercise.core.ecommerce.usecase;

import com.intilery.exercise.core.ecommerce.domain.BasketDetail;
import com.intilery.exercise.core.ecommerce.domain.OrderLine;
import com.intilery.exercise.core.ecommerce.repository.UserGraphRepository;
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
public class GetAbandonedBaskets {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetAbandonedBaskets(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public BasketDetail getAbandonedBaskets(String email) {
        Vertex vCustomer = userGraphRepository.getForUser(email);
        return toBaskets(vCustomer);
    }

    private BasketDetail toBaskets(final Vertex vCustomer) {
        BasketDetail basketDetail = new BasketDetail();
        basketDetail.setEmail(vCustomer.getProperty("email"));

        List<OrderLine> lines = new ArrayList<>();

        GremlinPipeline pipeOrder = new GremlinPipeline();
        pipeOrder.start(vCustomer);
        List<Edge> eCheckOuts = pipeOrder.outE("visit").inV().outE("check out").order((argument) ->
                ((DateTime) ((Pair<Edge, Edge>)argument).getB().getProperty("createdAt"))
                        .compareTo(((Pair<Edge, Edge>)argument).getA().getProperty("createdAt"))
        ).toList();
        DateTime curCheckOut = eCheckOuts.get(0).getProperty("createdAt");

        GremlinPipeline pipeProduct = new GremlinPipeline();
        pipeProduct.start(vCustomer);
        List<Vertex> vProducts = pipeProduct.outE("visit").inV().outE("add to basket").inV().toList();
        Set<Vertex> vSetProducts = new HashSet<>(vProducts);
        for(Vertex product: vSetProducts) {
            if(getAddToBasketTime(product).isAfter(curCheckOut)) {
                OrderLine orderLine = new OrderLine();
                orderLine.setName(product.getProperty("name"));
                orderLine.setImage(product.getProperty("image"));
                orderLine.setPrice(product.getProperty("price"));
                orderLine.setQty(getQty(product, curCheckOut));
                lines.add(orderLine);
            }
        }
        basketDetail.setBasket(lines);
        return basketDetail;
    }

    private int getQty(Vertex product, DateTime curCheckOut) {
        List<Edge> eAddToBaskets = (List<Edge>) product.getEdges(IN, "add to basket");
        int qty = 0;
        for(Edge eAdd: eAddToBaskets) {
            DateTime addToBasketTime = eAdd.getProperty("createdAt");
            if(addToBasketTime.isAfter(curCheckOut)) {
                qty += (int) eAdd.getProperty("qty");
            }
        }
        return qty;
    }

    private DateTime getAddToBasketTime(Vertex product) {
        List<Edge> eAddToBaskets = (List<Edge>) product.getEdges(IN, "add to basket");
        return eAddToBaskets.get(0).getProperty("createdAt");
    }
}
