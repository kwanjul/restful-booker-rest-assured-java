package com.restfulbooker.booking;

import com.restfulbooker.auth.AuthClient;
import com.restfulbooker.config.RequestSpecs;
import com.restfulbooker.data.BookingData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.restfulbooker.data.BookingData.booking;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingUpdateTest {

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
    public void putBookingWithValidTokenReplacesBookingDetails() {
        Map<String, Object> booking = BookingData.defaultBooking();
        bookingId = BookingClient.createBooking(booking);
        String token = AuthClient.getToken();

        given()
                .spec(RequestSpecs.baseSpec())
                .accept("application/json")
                .cookie("token", token)
                .body(BookingData.booking("Alex", "Smith", 275, false, "Late checkout"))
        .when()
                .put("/booking/{id}", bookingId)
        .then()
                .statusCode(200)
                .body("firstname", equalTo("Alex"))
                .body("lastname", equalTo("Smith"))
                .body("totalprice", equalTo(275))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-11-20"))
                .body("bookingdates.checkout", equalTo("2026-11-22"))
                .body("additionalneeds", equalTo("Late checkout"))
        ;
    }

    @Test
    public void patchBookingWithValidTokenUpdatesOnlyProvidedFields() {
        Map<String, Object> booking = BookingData.defaultBooking();
        bookingId = BookingClient.createBooking(booking);
        String token = AuthClient.getToken();

        given()
                .spec(RequestSpecs.baseSpec())
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
                .body("bookingdates.checkin", equalTo("2026-11-20"))
                .body("bookingdates.checkout", equalTo("2026-11-22"))
                .body("additionalneeds", equalTo("Breakfast"));
    }
}
