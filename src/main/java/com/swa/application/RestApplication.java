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
        String orderUrl = ApiGateWayUrl + "/order" + ApiVersion + "/orders";
        String shoppingCartCommand = ApiGateWayUrl + "/cart-command" + ApiVersion + "/carts";
        String shoppingCartQuery = ApiGateWayUrl + "/cart-query" + ApiVersion + "/carts";

        /**** 1. Add a number of products to the product service ****/
        System.out.println("\n\n--------------------------------------START ------------------------------------------\n\n");
        System.out.println("1 ----------- Adding a couple of products -----------------------");
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

        /** 3. Retrieve products in the product service ****/
        // get product1
        System.out.println("----------- getting product1 -> book -----------------------");
        Product product1 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
        System.out.println(product1);
        System.out.println("\n");

        // get product2
        System.out.println("----------- getting product2 -> shoes -----------------------");
        Product product2 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "2");
        System.out.println(product2);
        System.out.println("\n");

        /**** 2. Modify a product in the productservice ****/
        // modify product1's stock
        System.out.println("2 ----------- Increase product 1 stock to 200 and change product 2 price to 50 -----------------------");
        product1.setNumberInStock(200);
        restTemplate.put(productUrl, product1);

        product2.setPrice(50);
        restTemplate.put(productUrl, product2);
        System.out.println("\n");

        System.out.println("3. ----------- Retrieve modified products -----------------------");
        product1 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
        System.out.println("----------- get modified Product1 -> shoes with numberInStock changed to 200 -----------------------");
        System.out.println(product1);
        System.out.println("");
        product2 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "2");
        System.out.println("----------- get modified Product2 -> shoes with price changed to 50 -----------------------");
        System.out.println(product2);

        System.out.println("\n");

        /**** create shopping cart ****/
        System.out.println("----------- Create an empty shopping cart with cart number '0001' -----------------------");
        final String cartNumber = "0001";
        ShoppingCart cartN = new ShoppingCart();
        cartN.setShoppingCartNumber(cartNumber);
        restTemplate.postForLocation(shoppingCartCommand, cartN);

        // get the shopping cart
        ShoppingCart cart = restTemplate.getForObject(shoppingCartQuery + "/{cId}", ShoppingCart.class, cartNumber);
        System.out.println("----------- getting shopping cart -----------------------");
        System.out.println(cart);
        System.out.println("\n");

        System.out.println("4. ----------- Putting 8 of Product 1 and 5 of product 2 to the cart ---------");
        /**** 4. Put some products in the shoppingcart  ****/
        System.out.println("\n");

        //- Put 8 of product1 to the shopping cart
        CartProduct cartProduct1 = new CartProduct(
                product1.getProductNumber(),
                cart.getShoppingCartNumber(),
                8
        );

        restTemplate.put(shoppingCartCommand + "/product/add", cartProduct1);

        //- Also put 5 of product2 to the shopping cart
        CartProduct cartProduct2 = new CartProduct(
                product2.getProductNumber(),
                cart.getShoppingCartNumber(),
                5
        );

        restTemplate.put(shoppingCartCommand + "/product/add", cartProduct2);



        /**** 5. Retrieve and show the shopping cart  ****/
        // get the shopping cart from the shopping cart query service
        cart = restTemplate.getForObject(shoppingCartQuery+ "/{cartNumber}", ShoppingCart.class, cartNumber);
        System.out.println("5. ----------- getting the shopping cart with two products in it -----------------------");
        System.out.println(cart);
        System.out.println("\n");

        /**** 6. Delete one product from the shopping cart ****/
        // delete product2 from the shopping cart
        System.out.println("6. ----------- Deleting product 2 from the shopping cart -----------------------");
        restTemplate.put(shoppingCartCommand + "/product/remove", cartProduct2);
        System.out.println("\n");

        /**** 7. Change the quantity of one of the products in the shopping cart ****/
        // change the quantity of product1 in the shopping cart from 8 to 5
        System.out.println("7. ----------- change the quantity of product1 in the shopping cart from 8 to 5 ------");
        cartProduct1.setQuantity(5);
        restTemplate.put(shoppingCartCommand + "/product/change-quantity", cartProduct1);
        System.out.println("\n");


        /**** 8. Retrieve and show the shopping cart  ****/
        // get the updated shopping cart for display
        // add a sleep to allow for eventual consistency updating of the query service
        Thread.sleep(2000);
        ShoppingCart updatedCart = restTemplate.getForObject(shoppingCartQuery + "/{cartNumber}", ShoppingCart.class, cartNumber);
        System.out.println("8. ----------- getting the updated shopping cart, now with one product -----------------------");
        System.out.println(updatedCart);
        System.out.println("\n");


        /**** 9. Checkout the shopping cart  ****/
        String orderNumber = restTemplate.postForObject(shoppingCartCommand + "/{cartId}/checkout", null,  String.class, cartNumber);
        System.out.println("9. ----------- checking out the cart -----------");
        // retrieve the created order

        Order order = restTemplate.getForObject(orderUrl + "/{orderNumber}", Order.class, orderNumber);
        System.out.println("----------- Order summary -----------------------");
        System.out.println(order);
        System.out.println("\n");

        // Create a customer
        System.out.println("----------- Create a customer 'Kemal Abdella' -----------------------");
        Address address = new Address("1000 street", "Fairfield", "52557");
        Customer customer1 = new Customer(
                "customer1",
                "Kemal",
                "Abdella",
                "641 248 1243",
                "kemal.abdella@gmail.com",
                address
        );

        restTemplate.postForLocation(customerUrl, customer1);

        // get the added Customer
        Customer customer = restTemplate.getForObject(customerUrl + "/{cId}", Customer.class, customer1.getCustomerId());
        System.out.println("----------- getting customer1 -> Kemal Abdella -----------------------");
        System.out.println(customer);
        System.out.println("\n");

        /**** 10. Add customer to order  ****/
        System.out.println("10. ----------- Add customer to order -----------------------");
        OrderCustomerDto orderCustomerDto = new OrderCustomerDto(order.getOrderNumber(), customer.getCustomerId());
        restTemplate.postForLocation(orderUrl + "/add-customer", orderCustomerDto);
        System.out.println("\n");

        order = restTemplate.getForObject(orderUrl + "/{orderNumber}", Order.class, orderNumber);
        System.out.println("11. ----------- Order with Customer summary -----------------------");
        System.out.println(order);
        System.out.println("\n");

        /**** 12. Place the order  ****/
        restTemplate.postForLocation(orderUrl + "/place/{orderNumber}", null, order.getOrderNumber());
        System.out.println("12 ----------- Placing order -----------------------");

        order = restTemplate.getForObject(orderUrl + "/{orderNumber}", Order.class, orderNumber);
        System.out.println("----------- Placed order Details -----------------------");
        System.out.println(order);
        System.out.println("\n");

        // confirm product stock reduced
        // add a sleep to allow for eventual consistency updating of the product stock
        Thread.sleep(2000);
        Product product1updated = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
        System.out.println("----------- Confirm product 1 stock is reduced -----------------------");
        System.out.println("Product 1 before order placed:\n" + product1);
        System.out.println("");
        System.out.println("Product 1 after order placed:\n" + product1updated);

        System.out.println("\n\n---------------------------------- SUCCESS! ------------------------------------------\n\n");
    }

    @Bean
    RestOperations restTemplate() {
        return new RestTemplate();
    }
}
