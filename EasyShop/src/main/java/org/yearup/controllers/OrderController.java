package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;

/*@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrderController {
    private OrderDao orderDao;
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    public OrderController(OrderDao orderDao, ShoppingCartDao shoppingCartDao) {
        this.orderDao = orderDao;
        this.shoppingCartDao = shoppingCartDao;
    }

    //@PostMapping("")
    //@PreAuthorize("isAuthenticated()")
}

 */
