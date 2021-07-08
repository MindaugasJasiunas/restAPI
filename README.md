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
| GET          | /v2/api-docs |

## Usage

Project can be run from terminal using Maven wrapper

```http
./mvnw spring-boot:run
```

We can freely access user data with public information (no password or internal ID provided)

```http
GET /api/v1/users
GET /api/v1/users/{publicId}
```

To login(get JWT token) using user data, we must provide email & password to endpoint *'/authenticate'*
```http
POST /authenticate

{
    "email":    "john.doe@example.com",
    "password": "encryptedPassword"
}
```
In return we get JSON response with generated JWT token

```http
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyMzM0NDY3OSwiaWF0IjoxNjIzMzA4Njc5fQ.K0F9vsNSvzd5uqOf6Y7ZvU54WUBZL5GNQ6wPVvU5S1E"
}
```
Providing this token in *Authorization* header with `Bearer ` keyword used by default in Spring Security, we can access `POST`, `PUT`, `PATCH`, `DELETE`  HTTP methods for '*/users*' endpoints.
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyMzM0NDY3OSwiaWF0IjoxNjIzMzA4Njc5fQ.K0F9vsNSvzd5uqOf6Y7ZvU54WUBZL5GNQ6wPVvU5S1E
```

##

## API Reference

We can access REST API interactive documentation created with Swagger 2 using endpoint:
```http
GET /swagger-ui.html
```

### Get all users

#### Request

```http
GET /api/v1/users
```

#### Response
```http
[
    {
        "publicId": "c928f4b3-38aa-4e7d-b0f3-948b8cd78153",
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com"
    },
    {
        "publicId": "c7fc5255-3d3c-4b41-bffe-e34c6f599e7b",
        "firstName": "Jane",
        "lastName": "Doe",
        "email": "jane.doe@@example.com"
    },
    {
        "publicId": "69e66da7-5a68-4f19-8e61-852bd3b2f793",
        "firstName": "Oscar",
        "lastName": "Dean",
        "email": "oscar.dean@example.com"
    }
]
```

### Get user

#### Request

```http
GET /api/v1/users/${publicId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicId`      | `UUID` | **Required**. Public id of user to fetch |

#### Response

```http
{
    "publicId": "c928f4b3-38aa-4e7d-b0f3-948b8cd78153",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
}
```

### Authenticate

#### Request

```http
POST /authenticate

  {
    "email":    "john.doe@example.com",
    "password": "encryptedPassword"
  }
```

#### Response
```http
200 OK
Content-Type: application/json

  {
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyMzM0NDY3OSwiaWF0IjoxNjIzMzA4Njc5fQ.K0F9vsNSvzd5uqOf6Y7ZvU54WUBZL5GNQ6wPVvU5S1E"
  }
```

### Create user

#### Request

```http
POST /api/v1/users

  {
    "firstName":"Jane",
    "lastName":"Doe",
    "email":"jane.doe@example.com",
    "password":"SecurePassword"
  }
```

| Field | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `firstName`      | `string` | **Optional**. User first name |
| `lastName`      | `string` | **Optional**. User last name |
| `email`      | `string` | **Required**. User email |
| `password`      | `string` | **Required**. User password |

| Header | Format |Description     |
| :-------- | :-------- | :-------------------------------- |
| `Authorization` |Bearer <JWT>| **Required**. JSON Web Token |

#### Response
```http
201 Created
Content-Type: application/json

  {
    "publicId": "0386e5ed-a92b-42d3-8015-5a124633b565",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@example.com"
  }
```

### Update user

#### Request

```http
PUT /api/v1/users/${publicId}

  {
    "firstName":"JaneUpdated",
    "lastName":"DoeUpdated",
    "email":"jane.doe.updated@example.com",
    "password":"SecurePasswordUpdated"
  }
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicId`      | `UUID` | **Required**. Public id of user to fetch |


| Field | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `firstName`      | `string` | **Optional**. User first name |
| `lastName`      | `string` | **Optional**. User last name |
| `email`      | `string` | **Required**. User email |
| `password`      | `string` | **Required**. User password |

| Header | Format |Description     |
| :-------- | :-------- | :-------------------------------- |
| `Authorization` |Bearer <JWT>| **Required**. JSON Web Token |

#### Response
```http
201 Created
Content-Type: application/json

  {
    "publicId": "0386e5ed-a92b-42d3-8015-5a124633b565",
    "firstName":"JaneUpdated",
    "lastName":"DoeUpdated",
    "email":"jane.doe.updated@example.com"
  }
```

### Partially update user

#### Request

```http
PATCH /api/v1/users/${publicId}

  {
    "firstName":"JanePartialyUpdated",
    "lastName":"DoePartialyUpdated",
    "email":"jane.doe.PartialyUpdated@example.com",
    "password":"SecurePasswordPartialyUpdated"
  }
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicId`      | `UUID` | **Required**. Public id of user to fetch |


| Field | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `firstName`      | `string` | **Optional**. User first name |
| `lastName`      | `string` | **Optional**. User last name |
| `email`      | `string` | **Optional**. User email |
| `password`      | `string` | **Optional**. User password |

| Header | Format |Description     |
| :-------- | :-------- | :-------------------------------- |
| `Authorization` |Bearer <JWT>| **Required**. JSON Web Token |

#### Response
```http
201 Created
Content-Type: application/json

  {
    "publicId": "0386e5ed-a92b-42d3-8015-5a124633b565",
    "firstName":"JanePartialyUpdated",
    "lastName":"DoePartialyUpdated",
    "email":"jane.doe.PartialyUpdated@example.com"
  }
```

### Delete user

#### Request

```http
DELETE /api/v1/users/${publicId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicId`      | `UUID` | **Required**. Public id of user to fetch |

| Header | Format |Description     |
| :-------- | :-------- | :-------------------------------- |
| `Authorization` |Bearer <JWT>| **Required**. JSON Web Token |

#### Response
```http
204 No Content
```