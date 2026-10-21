package com.restfulbooker.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {

    private static final RequestSpecification SPEC =
            new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.getProperty("api.baseUrl"))
                    .setContentType("application/json")
                    .build();

    public static RequestSpecification baseSpec() {
        return SPEC;
    }

    private RequestSpecs() {
    }
}
