package com.restfulbooker.booking;

import com.restfulbooker.auth.AuthClient;
import com.restfulbooker.config.RequestSpecs;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class BookingClient {

    public static int createBooking(Map<String, Object> booking) {
        return given()
                    .spec(RequestSpecs.baseSpec())
                    .body(booking)
                .when()
                    .post("/booking")
                .then()
                    .statusCode(200)
                    .extract()
                    .path("bookingid");
    }

    public static void deleteBooking(int bookingId) {
        String token = AuthClient.getToken();

        given()
            .spec(RequestSpecs.baseSpec())
            .cookie("token", token)
        .when()
            .delete("/booking/{id}", bookingId)
        .then()
            .statusCode(201);
    }

    private BookingClient() {
    }
}
