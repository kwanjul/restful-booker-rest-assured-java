package com.restfulbooker.booking;

import com.restfulbooker.auth.AuthClient;
import com.restfulbooker.config.RequestSpecs;
import com.restfulbooker.data.BookingData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingDeleteTest {

    private Integer bookingId = null;

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        try {
            if (bookingId != null) {
                BookingClient.deleteBooking(bookingId);
            }
        } finally {
            bookingId = null;
        }
    }

    @Test
    public void deleteBookingWithValidTokenMakesBookingUnavailable() {
        Map<String, Object> booking = BookingData.defaultBooking();
        bookingId = BookingClient.createBooking(booking);
        String token = AuthClient.getToken();

        given()
                .spec(RequestSpecs.baseSpec())
                .cookie("token", token)
        .when()
                .delete("/booking/{id}", bookingId)
        .then()
                .statusCode(201);

        int deletedId = bookingId;
        bookingId = null;

        given()
                .spec(RequestSpecs.baseSpec())
        .when()
                .get("/booking/{id}", deletedId)
        .then()
                .statusCode(404);
    }
}
