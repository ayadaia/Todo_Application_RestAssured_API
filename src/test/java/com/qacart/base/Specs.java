package com.qacart.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static String getenv(){
        String BaseURL;
        String env = System.getProperty("env","PRODUCTION");
        switch (env) {
            case "PRODUCTION":
                BaseURL = "https://qacart-todo.herokuapp.com";
                break;
            case "LOCAL":
                BaseURL = "http://localhost:3000";
                break;
            default:
                throw new RuntimeException("Invalid environment: " + env);
        }
        return BaseURL;
    }
    public static RequestSpecification getRequestSpec(){
        return given()
                .log().all()
                .baseUri(getenv())
                .contentType(ContentType.JSON);
    }
}
