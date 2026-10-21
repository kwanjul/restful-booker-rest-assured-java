package com.restfulbooker.config;

public final class ApiConfig {
    private static final String DEFAULT_BASE_URL = "https://restful-booker.herokuapp.com";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "changeme";

    private ApiConfig() {
    }

    public static String baseUrl() {
        return configuredValue("api.baseUrl", "API_BASE_URL", DEFAULT_BASE_URL);
    }

    public static String username() {
        return configuredValue("api.username", "API_USERNAME", DEFAULT_USERNAME);
    }

    public static String password() {
        return configuredValue("api.password", "API_PASSWORD", DEFAULT_PASSWORD);
    }

    private static String configuredValue(String propertyName, String environmentName, String defaultValue) {
        String systemProperty = System.getProperty(propertyName);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String environmentVariable = System.getenv(environmentName);
        if (environmentVariable != null && !environmentVariable.isBlank()) {
            return environmentVariable;
        }

        return defaultValue;
    }
}
