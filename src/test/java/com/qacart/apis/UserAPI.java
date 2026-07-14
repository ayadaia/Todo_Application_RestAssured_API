package com.qacart.apis;

import com.qacart.base.Specs;
import com.qacart.data.Route;
import com.qacart.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPI {

    public static Response register(User user){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when().post(Route.REGISTER_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response login(User user){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when().post(Route.LOGIN_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
}
