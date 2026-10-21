package com.restfulbooker.booking;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class BookingUpdateTest {
    @Test
    public void putBookingWithValidTokenReplacesBookingDetails() {
        int bookingId = given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(booking("Jane", "Doe", 150, true, "Breakfast"))
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
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)
                .body(booking("Alex", "Smith", 275, false, "Late checkout"))
        .when()
                .put("/booking/{id}", bookingId)
        .then()
                .statusCode(200)
                .body("firstname", equalTo("Alex"))
                .body("lastname", equalTo("Smith"))
                .body("totalprice", equalTo(275))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-10-20"))
                .body("bookingdates.checkout", equalTo("2026-10-22"))
                .body("additionalneeds", equalTo("Late checkout"));
    }

    @Test
    public void patchBookingWithValidTokenUpdatesOnlyProvidedFields() {
        int bookingId = given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(booking("Jane", "Doe", 150, true, "Breakfast"))
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
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)
                .body(Map.of("firstname", "Alex"))
        .when()
                .patch("/booking/{id}", bookingId)
        .then()
                .statusCode(200)
                .body("firstname", equalTo("Alex"))
                .body("lastname", equalTo("Doe"))
                .body("totalprice", equalTo(150))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2026-10-20"))
                .body("bookingdates.checkout", equalTo("2026-10-22"))
                .body("additionalneeds", equalTo("Breakfast"));
    }

    private Map<String, Object> booking(String firstName, String lastName, int totalPrice,
                                        boolean depositPaid, String additionalNeeds) {
        return Map.of(
                "firstname", firstName,
                "lastname", lastName,
                "totalprice", totalPrice,
                "depositpaid", depositPaid,
                "bookingdates", Map.of(
                        "checkin", "2026-10-20",
                        "checkout", "2026-10-22"),
                "additionalneeds", additionalNeeds);
    }
}
