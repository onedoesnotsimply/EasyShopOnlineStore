package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("cart")
//@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            int userId = getUserId(principal);

            // use the shoppingcartDao to get all items in the cart and return the cart
            ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);
            return shoppingCart;
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    /*
    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{id}")
    @PreAuthorize("isAuthenticated()")
    public void addProductToCart(@PathVariable int id, Principal principal){

        int userId = getUserId(principal);

        ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);

        if (!shoppingCart.contains(id)) {
            shoppingCartDao.addProduct(userId,id,1);
        } else {
            shoppingCartDao.updateProduct(userId, id, shoppingCart.get(id).getQuantity() + 1);
        }
    }
     */

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{id}")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCartItem addProductToCart(@PathVariable int id, Principal principal){

        int userId = getUserId(principal);
        System.out.println(id);

        ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);

        if (!shoppingCart.contains(id)) {
            shoppingCartDao.addProduct(userId,id,1);
            //shoppingCart = shoppingCartDao.getByUserId(userId);

        } else {
            shoppingCartDao.updateProduct(userId, id, shoppingCart.get(id).getQuantity() + 1);
            //shoppingCart = shoppingCartDao.getByUserId(userId);
        }

        shoppingCart = shoppingCartDao.getByUserId(userId);

        return shoppingCart.get(id);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{id}")
    @PreAuthorize("isAuthenticated()")
    public void updateCartProduct(@PathVariable int id, Principal principal, @RequestBody ShoppingCartItem item){
        int userId = getUserId(principal);


        ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);

        if (shoppingCart.contains(id)){
            shoppingCartDao.updateProduct(userId, id, item.getQuantity());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item doesn't exist to update.");
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void deleteCartItems(Principal principal){

        int userId = getUserId(principal);

        shoppingCartDao.clearCart(userId);
    }

    private int getUserId(Principal principal){
        // get the currently logged in username
        String userName = principal.getName();
        // find database user by userId
        User user = userDao.getByUserName(userName);
        return user.getId();
    }

}
