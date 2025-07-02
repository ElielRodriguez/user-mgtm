# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
  mvn quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
  mvn package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
  mvn package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
  mvn package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
  mvn package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

# User Resource API

**Base Path:** `/users`

---

## GET `/users/all`

**Description:**  
Get all users (paginated).

**Query Parameters:**

| Name | Type | Required | Description   |
| ---- | ---- | -------- | ------------- |
| page | int  | yes      | Page number   |
| size | int  | yes      | Page size     |

**Responses:**
- **200 OK**  
  Returns a paginated list of users.
- **404 Not Found**  
  If no users are found.

---

## GET `/users/{id}`

**Description:**  
Get a user by their ID.

**Path Parameters:**

| Name | Type | Required | Description |
| ---- | ---- | -------- | ----------- |
| id   | long | yes      | User ID     |

**Responses:**
- **200 OK**  
  Returns the user’s details.
- **404 Not Found**  
  If the user is not found.

---

## GET `/users/country/{country}`

**Description:**  
Get users by country (paginated).

**Path Parameters:**

| Name    | Type   | Required | Description   |
| ------- | ------ | -------- | ------------- |
| country | string | yes      | Country name  |

**Query Parameters:**

| Name | Type | Required | Description   |
| ---- | ---- | -------- | ------------- |
| page | int  | yes      | Page number   |
| size | int  | yes      | Page size     |

**Responses:**
- **200 OK**  
  Returns users from the specified country.
- **404 Not Found**  
  If no users are found in that country.

---

## POST `/users`

**Description:**  
Create a new user.

**Request Body:**  
```json
UserPostRequest
````

**Responses:**

* **200 OK**
  User created successfully, or returns error details.

---

## PUT `/users/{id}`

**Description:**
Update an existing user.

**Path Parameters:**

| Name | Type | Required | Description |
| ---- | ---- | -------- | ----------- |
| id   | long | yes      | User ID     |

**Request Body:**

```json
UserUpdateRequest
```

**Responses:**

* **200 OK**
  User updated successfully, or returns error details.

---

## DELETE `/users/{id}`

**Description:**
Delete a user.

**Path Parameters:**

| Name | Type | Required | Description |
| ---- | ---- | -------- | ----------- |
| id   | long | yes      | User ID     |

**Responses:**

* **200 OK**
  User deleted successfully, or returns error details.

---

> **Note:**
> All responses are in JSON format and wrapped in a
> `Result<UserRequestResponse>` or `Result<List<UserRequestResponse>>` object.

```
```
