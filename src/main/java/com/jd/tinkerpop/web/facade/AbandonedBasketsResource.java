package com.jd.tinkerpop.web.facade;

import com.jd.tinkerpop.core.ecommerce.domain.BasketDetail;
import com.jd.tinkerpop.core.ecommerce.usecase.GetAbandonedBaskets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
public class AbandonedBasketsResource {

    private final GetAbandonedBaskets getAbandonedBaskets;

    @Autowired
    public AbandonedBasketsResource(GetAbandonedBaskets getAbandonedBaskets) {
        this.getAbandonedBaskets = getAbandonedBaskets;
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasketDetail getAbandonedBaskets(@PathVariable("email") String email) {
        return getAbandonedBaskets.getAbandonedBaskets(email);
    }
}
