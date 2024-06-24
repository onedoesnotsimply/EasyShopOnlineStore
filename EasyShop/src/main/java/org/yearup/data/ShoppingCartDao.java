package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    //ShoppingCartItem getItem(int)
    void addProduct(int userId, int productId, int quantity);
    void updateProduct(int userId, int productId, int quantity);
    void clearCart(int userId);
}
