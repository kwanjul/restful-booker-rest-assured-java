package com.restfulbooker.data;

import java.util.Map;

public final class BookingData {

    public static Map<String, Object> defaultBooking() {
        return booking("Jane", "Doe", 150, true, "Breakfast");
    }

    public static Map<String, Object> booking(String firstName, String lastName,
                                              int totalPrice, boolean depositPaid, String additionalNeeds) {
        return Map.of(
                "firstname", firstName,
                "lastname", lastName,
                "totalprice", totalPrice,
                "depositpaid", depositPaid,
                "bookingdates", Map.of(
                        "checkin", "2026-11-20",
                        "checkout", "2026-11-22"),
                "additionalneeds", additionalNeeds);
    }

    private BookingData() {}
}
