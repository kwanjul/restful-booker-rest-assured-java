# Restful Booker REST Assured Tests

Java API test automation using REST Assured, TestNG, and Maven against the
[Restful Booker API](https://restful-booker.herokuapp.com).

## Prerequisites

- Java 17 or later
- Maven 3.8 or later

## Run the tests

```bash
mvn test
```

The API base URL defaults to `https://restful-booker.herokuapp.com`. Override
it with either an environment variable or a Maven JVM system property:

```bash
API_BASE_URL=https://your-api.example.com mvn test
mvn test -Dapi.baseUrl=https://your-api.example.com
```

Authentication tests use Restful Booker's demo credentials by default. Set
`API_USERNAME` and `API_PASSWORD`, or pass `-Dapi.username=...` and
`-Dapi.password=...`, to use different credentials.

The initial tests check that `GET /ping` returns HTTP `201` with the `Created`
response body and that valid credentials return a non-empty authentication
token from `POST /auth`.
