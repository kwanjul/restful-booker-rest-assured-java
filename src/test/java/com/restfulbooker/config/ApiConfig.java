package com.restfulbooker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class ApiConfig {
    private static final String CONFIG_FILE = "test.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ApiConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found: " + CONFIG_FILE);
            }
            PROPERTIES.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load configuration file: " + CONFIG_FILE, exception);
        }
    }

    private ApiConfig() { }

    public static String getProperty(String property) {
        String value = System.getProperty(property);

        if (value == null || value.isBlank()) {
            String environmentVariable = property
                    .toUpperCase(Locale.ROOT)
                    .replace('.', '_');

            value = System.getenv(environmentVariable);
        }

        if (value == null || value.isBlank()) {
            value = PROPERTIES.getProperty(property);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Configuration property is missing: " + property);
        }

        return value;
    }
}
