package com.Orders;

import com.example.OrderClient;
import com.example.User;
import com.example.UserClient;
import com.example.UserCredentials;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListOfOrdersTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = User.getRandom();
        userClient.create(user);
    }


    @Test
    @Description("Получение заказов конкретного пользователя. Авторизованный пользователь")
    public void getListOfOrderAuthUser() {


        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");
        ValidatableResponse getList = orderClient.getListOfOrders(accessToken);

        int statusCode = getList.extract().statusCode();
        boolean ListOfOrders = getList.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("List of orders is not received", ListOfOrders);

    }


    @Test
    @Description("Получение заказов конкретного пользователя. Неавторизованный пользователь")
    public void getListOfOrderNonAuthUser() {

    ValidatableResponse response = orderClient.getListOfOrders("");

    int statusCode = response.extract().statusCode();
    boolean ListOfOrders = response.extract().path("success");

    assertThat("Status code is incorrect", statusCode, equalTo(401));
    assertFalse("List of orders is not received", ListOfOrders);

    }
}
