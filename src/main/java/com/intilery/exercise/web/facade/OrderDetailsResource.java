package com.intilery.exercise.web.facade;

import com.intilery.exercise.core.ecommerce.domain.OrderDetail;
import com.intilery.exercise.core.ecommerce.usecase.GetOrderDetails;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/orders")
public class OrderDetailsResource {

    private final GetOrderDetails getOrderDetails;

    @Autowired
    public OrderDetailsResource(GetOrderDetails getOrderDetails) {
        this.getOrderDetails = getOrderDetails;
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetail getOrderDetailsByEmail(@PathVariable("email") String email) throws IOException, JSONException {
        return getOrderDetails.getOrderDetails(email);
    }
}
