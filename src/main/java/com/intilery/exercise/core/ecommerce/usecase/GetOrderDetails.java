package com.intilery.exercise.core.ecommerce.usecase;

import com.intilery.exercise.core.ecommerce.domain.OrderDetail;
import com.intilery.exercise.core.ecommerce.repository.UserGraphRepository;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.io.graphson.GraphSONMode;
import com.tinkerpop.blueprints.util.io.graphson.GraphSONUtility;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetOrderDetails {

    private final UserGraphRepository userGraphRepository;

    @Autowired
    public GetOrderDetails(UserGraphRepository userGraphRepository) {
        this.userGraphRepository = userGraphRepository;
    }

    public OrderDetail getOrderDetails(String email) throws JSONException{
        Vertex vertex = userGraphRepository.getForUser(email);
        JSONObject json = GraphSONUtility.jsonFromElement(vertex, null, GraphSONMode.COMPACT);
        System.out.println(json.toString());
        return null;
    }
}
