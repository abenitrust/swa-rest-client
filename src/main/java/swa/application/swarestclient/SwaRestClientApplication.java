package swa.application.swarestclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import swa.application.swarestclient.domain.*;

@SpringBootApplication
public class SwaRestClientApplication implements CommandLineRunner {

    @Autowired
    ClientInterface client;

    private final String baseUrl = "http://localhost:8080";

    public static void main(String[] args) {
        SpringApplication.run(SwaRestClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=============Get Products=============");
        System.out.println(client.getProducts());

        System.out.println("=============adding Product: Macbook...=============");
        Product product= new Product();
        product.setName("MacBook pro");
        product.setPrice(2000.0);
        product.setDescription("256 gb storage 16 RAM");
        product.setNumberInStock(10);
        client.addProduct(product);

        System.out.println("=============adding Product: Samsung 21...=============");
        Product prod2= new Product();
        prod2.setName("Samsung 21");
        prod2.setPrice(1200.0);
        prod2.setDescription("128gb 8 RAM");
        prod2.setNumberInStock(4);
        client.addProduct(prod2);

        System.out.println("============= Get All Products=============");
        var allProducts= client.getProducts();
        System.out.println(allProducts);

        //edit product
        System.out.println("==Edit Product from Macbook to lenovo yoga======");
        Product product1 = allProducts.getProducts().get(0);
        Product product2 = allProducts.getProducts().get(1);
        product1.setName("Lenovo Yoga");
        product1.setPrice(1200.0);
        product1.setDescription("125 gb storage 16 RAM");
        product1.setNumberInStock(5);
        client.modifyProduct(product1,product1.getProductNumber());
        var editedProduct= client.getProducts();
        System.out.println(editedProduct);
//
//
        //create and get cutomers
        System.out.println("============= Create Customer ... =============");
        Customer cust1= new Customer();
        cust1.setFirstName("Serke");
        cust1.setLastName("H");
        cust1.setEmail("se@gmail.com");
        cust1.setPhone("1243215436");
        cust1.setAddress(new Address("1000 North st", "Fairfield", "52557"));
        restTemplate().postForLocation(baseUrl+"/customer/save", cust1, Customer.class);
        System.out.println("============= get Customer number one=============");
        var customers= restTemplate().getForObject(baseUrl+"/customer/findall", Customers.class);
        var customer1= customers.getCustomerList().get(0);
        System.out.println(customer1);

//
        //Create shopping cart to a customer
        System.out.println("============= Create cart to Customer1 ...=============");
        restTemplate().postForLocation(baseUrl+"/cart/addCartForACustomer/"+ customer1.getCustomerId(), null, ShoppingCart.class);

        //add product(Hp) to cart of customer1
        System.out.println("============= Put product1 to cart of customer1 .... =============");
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getCustomerId()+"/quantity/"+ 4, product1, Products.class);
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getCustomerId()+"/quantity/"+ 2, product2, Products.class);

        // Show the shopping cart of customer1
        System.out.println("============= Get shopping cart of customer1=============");
        ShoppingCart cart= restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getCustomerId(), ShoppingCart.class);
        System.out.println(cart);

        // delete product1(Hp) from cart
        System.out.println("============= Deleting product 1(Hp) from cart of customer1..=============");
        restTemplate().delete(baseUrl+ "/cart/removeProductFromCart/"+customer1.getCustomerId()+"/product/"+product2.getProductNumber(), Void.class);

        //Change the quantity of one of the products
        int quantity2= 1;
        restTemplate().delete(baseUrl+"/cart/removeProductFromCartWithQuantity/"+customer1.getCustomerId()+"/product/"+product1.getProductNumber()+"/quantity/"+quantity2, product1, Products.class);

//        Retrieve and show the shoppingCart
        System.out.println(restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getCustomerId(), ShoppingCart.class));
//

        //Checkout the shoppingCart
        System.out.println("== Checkout and place an order ==");

        Order order = restTemplate().postForObject(baseUrl+"/cart/checkout/"+customer1.getCustomerId(),null, Order.class);
//
//        //Order palced
        System.out.println("============= Placing an order .... =============");
        restTemplate().postForObject(baseUrl+"/order/placeOrder/orderNumber/" + order.getOrderNumber(),customer1, Void.class);
//
        //Retrieve and show the shoppingcart
        System.out.println("============= Getting shopping cart (should be empty) .... =============");
        System.out.println(restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+ customer1.getCustomerId(), ShoppingCart.class));

        System.out.println("============= Get all order =============");
        System.out.println(restTemplate().getForObject(baseUrl+"/order/getOrders", Orders.class));

    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
