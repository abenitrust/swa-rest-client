package swa.application.swarestclient;

import swa.application.swarestclient.domain.Product;
import swa.application.swarestclient.domain.Products;

public interface ClientInterface {
    void addProduct(Product product);
    Product modifyProduct(Product product ,String productId) ;
    Products getProducts();
    void addProductToShoppingCart(String customerId,Product product);
    void showShoppingCart(String customerId);
    void removeProductFromShoppingCart(String customerId,String product);
    void checkoutShoppingCart(String customerId);
    void placeAnOrder(String orderNumber);

}
