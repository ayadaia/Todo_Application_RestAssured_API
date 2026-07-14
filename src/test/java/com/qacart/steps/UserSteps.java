package com.qacart.steps;

import com.github.javafaker.Faker;
import com.qacart.apis.UserAPI;
import com.qacart.models.User;
import io.restassured.response.Response;

public class UserSteps {

    public static User generateUser() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678";

        return new User(firstName, lastName, email, password);
    }

    public static User getRegisteredUser(){
        User user = generateUser();
        UserAPI.register(user);
        return user;
    }

    public static String getUserToken(){
        User user = generateUser();
        Response response =UserAPI.register(user);
        return response.body().path("access_token");
    }
}
