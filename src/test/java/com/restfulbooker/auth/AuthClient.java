package com.restfulbooker.auth;

import com.restfulbooker.config.ApiConfig;
import com.restfulbooker.config.RequestSpecs;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public final class AuthClient {

    public static String getToken() {
        return given()
            .spec(RequestSpecs.baseSpec())
            .body(Map.of(
                    "username", ApiConfig.getProperty("api.username"),
                    "password", ApiConfig.getProperty("api.password")))
        .when()
            .post("/auth")
        .then()
            .statusCode(200)
            .body("token", not(emptyOrNullString()))
            .extract()
            .path("token");
    }

    private AuthClient() {
    }
}
