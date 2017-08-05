package com.intilery.exercise.core.ecommerce.repository;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class UserGraphRepository {
    public Vertex getForUser(String email) {
        Graph graph = new TinkerGraph();

        Vertex customer = graph.addVertex(null);
        customer.setProperty("email", email);
        customer.setProperty("type", "customer");

        Vertex sourpussMaidenOfTheSeaCushion = addProduct(graph, "Sourpuss Maiden of the Sea Cushion", 15.99, "http://www.bigtattooplanet.com/store/media/catalog/product/cache/1/small_image/150x150/c96a280f94e22e3ee3823dd0a1a87606/s/p/sppi3_web.jpg");
        Vertex wolverineMoonAlchemyPendant = addProduct(graph, "Wolverine Moon Alchemy Gothic Pendant", 14.52, "http://www.bigtattooplanet.com/store/media/catalog/product/cache/1/small_image/150x150/c96a280f94e22e3ee3823dd0a1a87606/P/2/P229.jpg");
        Vertex diamondAndSwordsSweater = addProduct(graph, "Diamond and Swords Sweater", 35.00, "http://www.bigtattooplanet.com/store/media/catalog/product/cache/1/small_image/150x150/c96a280f94e22e3ee3823dd0a1a87606/l/i/lizbshoot010913-021-edit.jpg");
        Vertex holdFastPirateHat = addProduct(graph, "Six Bunnies Hold Fast Pirate Kid's Cap", 12.99, "http://www.bigtattooplanet.com/store/media/catalog/product/cache/1/thumbnail/82x82/c96a280f94e22e3ee3823dd0a1a87606/s/b/sbcap009.jpg");

        Vertex visit1 = addVisit(graph, customer, new DateTime(2013, 9, 1, 12, 10));
        addToBasket(graph, visit1, sourpussMaidenOfTheSeaCushion, 2, new DateTime(2013, 9, 1, 12, 20));
        checkout(graph, visit1, "ABC1", new DateTime(2013, 9, 1, 12, 30));
        addToBasket(graph, visit1, sourpussMaidenOfTheSeaCushion, 1, new DateTime(2013, 9, 1, 12, 33));

        Vertex visit2 = addVisit(graph, customer, new DateTime(2013, 9, 4, 11, 15));
        addToBasket(graph, visit2, wolverineMoonAlchemyPendant, 1, new DateTime(2013, 9, 4, 11, 20));

        Vertex visit3 = addVisit(graph, customer, new DateTime(2013, 9, 4, 16 ,22));
        addToBasket(graph, visit3, sourpussMaidenOfTheSeaCushion, 1, new DateTime(2013, 9, 4, 16, 24));
        checkout(graph, visit3, "ABC2", new DateTime(2013, 9, 4, 16, 25));
        addToBasket(graph, visit3, diamondAndSwordsSweater, 1, new DateTime(2013, 9, 4, 16, 27));
        addToBasket(graph, visit3, holdFastPirateHat, 1, new DateTime(2013, 9, 4, 16, 30));

        return customer;
    }

    private void checkout(Graph graph, Vertex visit, String orderID, DateTime when) {
        Vertex order = graph.addVertex(null);
        order.setProperty("type", "order");
        order.setProperty("orderID", orderID);

        Edge e = graph.addEdge(null, visit, order, "check out");
        e.setProperty("createdAt", when);
    }

    private void addToBasket(Graph graph, Vertex visit, Vertex product, int qty, DateTime when) {
        Edge e = graph.addEdge(null, visit, product, "add to basket");
        e.setProperty("qty", qty);
        e.setProperty("createdAt", when);
    }

    private Vertex addProduct(Graph graph, String name, double price, String image) {
        Vertex product = graph.addVertex(null);
        product.setProperty("type", "product");
        product.setProperty("name", name);
        product.setProperty("price", price);
        product.setProperty("image", image);
        return product;
    }

    private Vertex addVisit(Graph graph, Vertex customer, DateTime visitStarted) {
        Vertex visit = graph.addVertex(null);
        visit.setProperty("type", "visit");
        visit.setProperty("sessionStarted", visitStarted);
        graph.addEdge(null, customer, visit, "visit");
        return visit;
    }
}
