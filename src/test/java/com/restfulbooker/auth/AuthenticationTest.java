package com.restfulbooker.auth;

import com.restfulbooker.config.ApiConfig;
import com.restfulbooker.config.RequestSpecs;
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
                .spec(RequestSpecs.baseSpec())
                .body(Map.of(
                        "username", ApiConfig.getProperty("api.username"),
                        "password", ApiConfig.getProperty("api.password")))
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));
    }

    @Test
    public void invalidCredentialsNoToken() {
        given()
                .spec(RequestSpecs.baseSpec())
                .body(Map.of(
                        "username", ApiConfig.getProperty("api.username"),
                        "password", "BadPassword"))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
