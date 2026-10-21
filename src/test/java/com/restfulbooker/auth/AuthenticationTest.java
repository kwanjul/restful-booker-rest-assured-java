package com.restfulbooker.auth;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationTest {
    @Test
    public void validCredentialsReturnToken() {
        given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(Map.of(
                        "username", ApiConfig.username(),
                        "password", ApiConfig.password()))
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));
    }

    @Test
    public void invalidCredentialsNoToken() {
        given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(Map.of(
                        "username", ApiConfig.username(),
                        "password", "BadPassword"))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
