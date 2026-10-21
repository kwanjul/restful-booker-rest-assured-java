package com.restfulbooker.booking;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class BookingDeleteTest {
    @Test
    public void deleteBookingWithValidTokenMakesBookingUnavailable() {
        int bookingId = given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(Map.of(
                        "firstname", "Jane",
                        "lastname", "Doe",
                        "totalprice", 150,
                        "depositpaid", true,
                        "bookingdates", Map.of(
                                "checkin", "2026-10-20",
                                "checkout", "2026-10-22"),
                        "additionalneeds", "Breakfast"))
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .extract()
                .path("bookingid");

        String token = given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(Map.of(
                        "username", ApiConfig.username(),
                        "password", ApiConfig.password()))
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()))
                .extract()
                .path("token");

        given()
                .baseUri(ApiConfig.baseUrl())
                .cookie("token", token)
        .when()
                .delete("/booking/{id}", bookingId)
        .then()
                .statusCode(201);

        given()
                .baseUri(ApiConfig.baseUrl())
        .when()
                .get("/booking/{id}", bookingId)
        .then()
                .statusCode(404);
    }
}
