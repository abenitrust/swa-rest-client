package com.swa.application;

import com.swa.application.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class RestApplication implements CommandLineRunner {

    @Autowired
    private RestOperations restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        final String ApiGateWayUrl = "http://localhost:8002";
        final String ApiVersion = "/api/v1";

        String customerUrl = ApiGateWayUrl + "/customer" + ApiVersion + "/customers";
        String productUrl = ApiGateWayUrl + "/product" + ApiVersion + "/products";
        String orderUrl = ApiGateWayUrl + "/order" + ApiVersion + "/ordres";
        String shoppingCartCommand = ApiGateWayUrl + "/cart-command" + ApiVersion + "/carts";
        String shoppingCartQuery = ApiGateWayUrl + "/cart-query" + ApiVersion + "/carts";

        /**** 1. Add a number of products to the product service ****/
        //add product1 - book
        restTemplate.postForLocation(productUrl, new Product(
                "1",
                "Book",
                10.20,
                "Amazing book",
                100
        ));

        //add product2 - shoes
        restTemplate.postForLocation(productUrl, new Product(
                "2",
                "Shoes",
                44.43,
                "High class shoes from Italy",
                150
        ));

        /** 2. Retrieve products in the product service ****/
        // get product1
        System.out.println("----------- getting product1 -> book -----------------------");
        Product product1 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
        System.out.println(product1);

        // get product2
        System.out.println("----------- getting product2 -> shoes -----------------------");
        Product product2 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "2");
        System.out.println(product2);


        /**** 3. Modify a product in the productservice ****/
        // modify product1's stock
        product1.setNumberInStock(200);
        restTemplate.put(productUrl, product1);

        // get and confirm modified product
        Product modifiedProduct = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
        System.out.println("----------- get modified Product1 -> shoes with numberInStock changed to 200 -----------------------");
        System.out.println(modifiedProduct);



        // Create a customer
        Customer customer1 = new Customer(
                "customer1",
                "Suzy",
                "James",
                "1000 street",
                "Fairfield",
                "52557",
                "12345678",
                "suzyjames@gmail.com"
        );
        try {
            restTemplate.postForLocation(customerUrl, customer1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // get the added Customer
        Customer customer = restTemplate.getForObject(customerUrl + "/{cId}", Customer.class, "customer1");
        System.out.println("----------- getting customer1 -> Suzy James -----------------------");
        System.out.println(customer);



        /**** 10. Add customer to order ****/
        // - First create a shopping cart for customer1 using the shopping cart command service
        Long shoppingCartNumber = 1L;
        ShoppingCartCustomer shoppingCartCustomer = new ShoppingCartCustomer(customer1.getcId(), shoppingCartNumber);
        restTemplate.postForLocation(shoppingCartCommand, shoppingCartCustomer);



        /**** 4. Put some products in the shoppingcart  ****/
        //- Put product1 to the shopping cart
        ShoppingCartProduct shoppingCartProduct1 = new ShoppingCartProduct(
                Long.parseLong(product1.getProductNumber()),
                8,
                product1.getPrice());
        restTemplate.postForLocation(shoppingCartCommand + "/{cartId}/products", shoppingCartProduct1, shoppingCartNumber);

        //- Also put product2 to the shopping cart
        ShoppingCartProduct shoppingCartProduct2 = new ShoppingCartProduct(
                Long.parseLong(product2.getProductNumber()),
                23,
                product2.getPrice());
        restTemplate.postForLocation(shoppingCartCommand + "/{cartId}/products", shoppingCartProduct2, shoppingCartNumber);



        /**** 5. Retrieve and show the shopping cart  ****/
        // get the shopping cart from the shopping cart query service
        ShoppingCart shoppingCart = restTemplate.getForObject(shoppingCartQuery+ "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
        System.out.println("----------- getting the shopping cart with two products in it -----------------------");
        System.out.println(shoppingCart);


        /**** 7. Change the quantity of one of the products in the shopping cart ****/
        // change the quantity of product1 in the shopping cart from 8 to 5
        restTemplate.put(shoppingCartCommand, new Quantity(5), shoppingCartNumber, product1.getProductNumber());


        /**** 6. Delete one product from the shopping cart ****/
        // delete product2 from the shopping cart
        restTemplate.delete(shoppingCartCommand + "/{cartId}/products/{productNumber}", shoppingCartNumber, product2.getProductNumber());



        /**** 8. Retrieve and show the shopping cart  ****/
        // get the updated shopping cart for display
        // add a sleep to allow for eventual consistency updating of the query service
        Thread.sleep(2000);
        ShoppingCart shoppingCartUpdated = restTemplate.getForObject(shoppingCartQuery + "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
        System.out.println("----------- getting the updated shopping cart, now with one product -----------------------");
        System.out.println(shoppingCartUpdated);



        /**** 9. Checkout the shopping cart  ****/
        String checkoutMessage = restTemplate.getForObject(shoppingCartQuery + "/{cartId}/checkout",  String.class, shoppingCartNumber);
        System.out.println("----------- checking out and sending the order to order service -----------");
        /**** 12. Place the order  ****/
        System.out.println(checkoutMessage);



        /**** 11. Retrieve and show the order  ****/
        // retrieve order for customer1
        Orders order = restTemplate.getForObject(orderUrl + "/customers/{customerID}", Orders.class, "customer1");
        System.out.println("----------- getting order1 for customer1 -----------------------");
        System.out.println(order);

    }

    @Bean
    RestOperations restTemplate() {
        return new RestTemplate();
    }
}
