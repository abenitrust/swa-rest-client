package swa.application.swarestclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import swa.application.swarestclient.domain.Product;
import swa.application.swarestclient.domain.Products;
import swa.application.swarestclient.domain.ShoppingCart;

import java.net.URI;

@Service
public class ClientInterfaceImpl implements ClientInterface{



    @Autowired
    private RestTemplate restTemplate;

    private final String baseProductUrl = "http://localhost:8080/products";

    private final String addProductToShoppingCartUrl = "http://localhost:8080/cart/{customerId}";
    private final String showShoppingCartUrl = "http://localhost:8080/cartQuery/getShoppingCart/{customerId}";
    private final String removeProductFromShoppingCartUrl = "http://localhost:8080/cart";
    private final String checkoutShoppingCartUrl = "http://localhost:8080/cart/checkout/{customerId}";
    private final String placeAnOrderUrl = "http://localhost:8080/order/placeOrder/orderNumber/{orderNumber}";


    @Override
    public void addProduct(Product product) {
        URI uri = restTemplate.postForLocation(baseProductUrl, product, Product.class);
    }

    @Override
    public Product modifyProduct(Product product, String productId ) {
        restTemplate.put(baseProductUrl+"/"+productId, product, Product.class);
        return product;
    }

    @Override
    public Products getProducts() {
        var products= restTemplate.getForObject(baseProductUrl, Products.class);

        return products;
    }

    @Override
    public void addProductToShoppingCart(String customerId,Product product) {
        URI uri = restTemplate.postForLocation(addProductToShoppingCartUrl,product, customerId);
        System.out.println(uri);

    }

    @Override
    public void showShoppingCart(String customerId) {

        restTemplate.getForObject(showShoppingCartUrl, ShoppingCart.class, customerId);

    }

    @Override
    public void removeProductFromShoppingCart(String customerId, String productId) {
        restTemplate.delete(removeProductFromShoppingCartUrl, customerId , productId);
    }

    @Override
    public void checkoutShoppingCart(String customerId) {
        URI uri = restTemplate.postForLocation(checkoutShoppingCartUrl, customerId);
        System.out.println(uri);

    }

    @Override
    public void placeAnOrder(String orderNumber) {
        URI uri = restTemplate.postForLocation(placeAnOrderUrl, orderNumber);
        System.out.println(uri);


    }








}

