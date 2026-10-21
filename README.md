# Restful Booker API Test Automation Framework

A Java API test automation project built with **REST Assured**, **TestNG**, and **Maven** against the public [Restful Booker API](https://restful-booker.herokuapp.com). The project demonstrates API functional testing, reusable test utilities, configuration management, response validation, and test-data cleanup.

## Technology stack

- Java 17
- REST Assured 5.5.6
- TestNG 7.11.0
- Maven
- Jackson Databind

## Test coverage

The current suite contains **9 tests** covering:

| Area | Scenarios |
| --- | --- |
| Health check | Verify the API health-check endpoint |
| Authentication | Verify valid and invalid credentials |
| Booking creation | Create a booking and validate the response |
| Booking retrieval | Retrieve booking IDs and retrieve booking details by ID |
| Booking updates | Update an entire booking (PUT) and selected fields (PATCH) |
| Booking deletion | Delete a booking and verify it is no longer available |

Tests validate relevant HTTP status codes, response content, and booking data. Booking tests use generated booking IDs rather than depending on a pre-existing record.

## Framework structure

The test suite is organized under `src/test/java/com/restfulbooker/`:

| Component | Responsibility |
| --- | --- |
| `config/ApiConfig` | Loads settings from `test.properties`, environment variables, and Java system properties |
| `config/RequestSpecs` | Provides a shared REST Assured request specification |
| `auth/AuthClient` | Requests authentication tokens for tests requiring authorization |
| `booking/BookingClient` | Provides reusable booking creation and deletion operations for test setup and cleanup |
| `data/BookingData` | Builds default and customized booking request data |
| Test classes | Exercise the API endpoints and assert expected behavior |

Booking tests use TestNG `@AfterMethod` cleanup where applicable to remove bookings created during testing. The test for deletion itself sends the DELETE request directly so that the operation remains under test.

## Prerequisites

- Java 17 or later
- Maven 3.8 or later
- Network access to the Restful Booker API

## Running the tests

From the project root, run:

```bash
mvn test
```

Maven Surefire writes test results to `target/surefire-reports/`.

## Configuration

Default settings are defined in `src/test/resources/test.properties`:

```properties
api.baseUrl=https://restful-booker.herokuapp.com
api.username=admin
api.password=password123
```

These are public demo credentials provided for the Restful Booker test API; do not store private credentials in this file.

Settings can be overridden using environment variables or Java system properties. Java system properties take precedence over environment variables, which take precedence over `test.properties`.

**Override the API base URL:**

```bash
API_BASE_URL=https://your-api.example.com mvn test
```

Or:

```bash
mvn test -Dapi.baseUrl=https://your-api.example.com
```

**Override authentication credentials:**

```bash
API_USERNAME=your-username API_PASSWORD=your-password mvn test
```

Or:

```bash
mvn test -Dapi.username=your-username -Dapi.password=your-password
```

## Notes

Restful Booker is a public demonstration API. Because it is a shared external service, test execution depends on its availability and behavior. The framework creates test bookings as needed and cleans them up where applicable to reduce dependencies between tests.
