package com.User;

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


public class LoginUserTest {

    private UserClient userClient;
    private boolean isUserAuthorized;
    User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }


    @Test
    @Description("Логин под существующим пользователем")
    public void checkCourierLogin() {

        user = User.getRandom();
        userClient.create(user);

        ValidatableResponse response = userClient.login(UserCredentials.from(user));

        int statusCode = response.extract().statusCode();
        boolean isUserAuthorized = response.extract().path("success");


        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User is not authorized", isUserAuthorized);
    }


    @Test
    @Description("Логин с неверным логином и паролем")
    public void checkUserLoginWithUncorrectEmailAndPassword() {

        user = User.getRandom();
        userClient.create(user);
        user = User.getWithEmailAndPassword();

        ValidatableResponse response = userClient.login(UserCredentials.from(user));

        int statusCode = response.extract().statusCode();
        boolean isUserAuthorized = response.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertFalse("User is authorized", isUserAuthorized);

    }

}
