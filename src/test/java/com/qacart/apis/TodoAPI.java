package com.qacart.apis;

import com.qacart.base.Specs;
import com.qacart.data.Route;
import com.qacart.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoAPI {

    public static Response addTodo(Todo todo , String token){
       return given()
               .spec(Specs.getRequestSpec())
                .header("Authorization","Bearer "+token)
                .body(todo)
                .when().post(Route.ADD_TODO_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getTodo(String taskID , String token){
        return  given()
                .spec(Specs.getRequestSpec())
                .header("Authorization","Bearer "+token)
                .when().get(Route.ADD_TODO_ROUTE+"/"+taskID)
                .then()
                .extract().response();
    }

    public static Response deleteTodo(String taskID , String token){
        return    given()
                .spec(Specs.getRequestSpec())
                .header("Authorization","Bearer "+token)
                .when().delete(Route.ADD_TODO_ROUTE+"/"+taskID)
                .then()
                .extract().response();
    }
}
