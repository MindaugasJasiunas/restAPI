# REST API using Spring Boot and JWT

REST API written in Java using Spring Boot framework & Java Web Token for authorization.

## HTTP endpoints

| Method        | Endpoint      |
| ------------- |:------------- |
| GET           | /api/v1/users |
| GET           | /api/v1/users/{publicId} |
| POST          | /api/v1/users |
| PUT           |/api/v1/users/{publicId} |
| PATCH         | /api/v1/users/{publicId} |
| DELETE        | /api/v1/users/{publicId} |
| POST          | /authenticate |

## Usage

Project can be run from terminal using Maven wrapper

```bash
./mvnw spring-boot:run
```

We can freely access user data with public information (no password or internal ID provided)

```
[GET] localhost:8080/api/v1/users
[GET] localhost:8080/api/v1/users/{publicId}
```

To login(get JWT token) using user data, we must provide email & password to endpoint *'/authenticate'*
```
[POST] localhost:8080/authenticate

{
    "email":    "john.doe@example.com",
    "password": "encryptedPassword"
}
```
In return we get JSON response with generated JWT token

```
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyMzM0NDY3OSwiaWF0IjoxNjIzMzA4Njc5fQ.K0F9vsNSvzd5uqOf6Y7ZvU54WUBZL5GNQ6wPVvU5S1E"
}
```
Providing this token in *Authorization* header with `Bearer ` keyword used by default by Spring Security, we can access `POST`, `PUT`, `PATCH`, `DELETE`  HTTP methods for '*/users*' endpoints.
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyMzM0NDY3OSwiaWF0IjoxNjIzMzA4Njc5fQ.K0F9vsNSvzd5uqOf6Y7ZvU54WUBZL5GNQ6wPVvU5S1E
```

##