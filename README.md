# Scrumptious Auth Service

Microservice to handle user account persistence and authentication with JWT headers.

The service runs on port 8000 and requires a local instance of MySQL running on port 3306 with a database schema named `scrumptious`.

The service may also be ran through the routing service, in which case the host url will be `http://localhost:8080/auth`. Note that the routing service must be running as well.

After the above is accomplished, the service can be started with `mvn spring-boot:run` or `mvn spring-boot:start` to fork a new process.

Unit tests for the service can be ran with `mvn clean package`.

## Authentication

Authentication is accomplished via `host_url/login` ie `http://localhost:8000/login` (or `http://localhost:8080/auth/login` through the router). This will validate credentials and return a JWT in the `Authorization` header.

## Additional Services

This service is additionally responsible for maintaining user account records ([see User](./src/main/java/com/ss/scrumptious_auth/entity/User.java)).
