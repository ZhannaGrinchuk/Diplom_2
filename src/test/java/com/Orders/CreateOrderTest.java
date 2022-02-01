package com.Orders;

import com.example.*;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;



public class CreateOrderTest {

    private UserClient userClient;
    private User user;
    private OrderClient orderClient;
    private Ingredients ingredients;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();

    }

    @Test
    @Description("Создание заказа с авторизацией")
    public void checkOrderCreatingAuthUser() {

        user = User.getRandom();
        userClient.create(user);
        ingredients = Ingredients.getBurger();

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");


       ValidatableResponse createOrder = orderClient.createOrder(accessToken, ingredients);

        int statusCode = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        int orderNumber = createOrder.extract().path("order.number");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Order is not created", isOrderCreated);
        assertThat("Order number is missing", orderNumber, is(CoreMatchers.not(0)));

    }

    @Test
    @Description("Создание заказа без авторизации")
    public void checkOrderCreatingNonAuthUser() {

        ingredients = Ingredients.getBurger();

        ValidatableResponse createOrder = orderClient.createOrder("", ingredients);

        int statusCode = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        int orderNumber = createOrder.extract().path("order.number");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Order is not created", isOrderCreated);
        assertThat("Order number is missing", orderNumber, is(CoreMatchers.not(0)));

    }

    @Test
    @Description("Создание заказа с ингредиентами")
    public void checkOrderCreatingWithIngredients() {

        user = User.getRandom();
        userClient.create(user);
        ingredients = Ingredients.getBurger();

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");

        ValidatableResponse createOrder = orderClient.createOrder(accessToken, ingredients);

        int statusCode = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        int orderNumber = createOrder.extract().path("order.number");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Order is not created", isOrderCreated);
        assertThat("Order number is missing", orderNumber, is(CoreMatchers.not(0)));

    }

    @Test
    @Description("Создание заказа без ингредиентов")
    public void checkOrderCreatingWithoutIngredients() {

        user = User.getRandom();
        userClient.create(user);
        ingredients = Ingredients.getNullIngredients();

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");

        ValidatableResponse createOrder = orderClient.createOrder(accessToken, ingredients);

        int statusCode = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(400));
        assertFalse("Order is created", isOrderCreated);
    }

    @Description("Создание заказа с неверным хэшем ингредиентов")
    @Test
    public void checkOrderCreatingWithIncorrectHashOfIngredients() {

        user = User.getRandom();
        userClient.create(user);
        ingredients = Ingredients.getIncorrectIngredients();

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");
        ValidatableResponse createOrder = orderClient.createOrder(accessToken, ingredients);

        int statusCode = createOrder.extract().statusCode();
        assertThat("Status code is incorrect", statusCode, equalTo(500));
    }
}
