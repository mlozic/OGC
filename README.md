# OGC
Spring Boot BE Assignment

# Steps for testing:
Start MySQL by positioning in folder with docker-compose.yml

docker compose up

Open secret-store project

mvn clean spring-boot:run

mvn -version 3.9.9

Spring boot 3.4.2

Java 17

# Postman
There is postman collection for API interactions import it in postman

create user

authenticate user 

copy jwt from response header and paste it in authorization under Auth Type "Bearer token"

CRUD passwords

CRUD users

keep in mind if you update user after it is authenticated you will need to authenticate again with updated data

Roles are "MANAGEMENT", "DEV_OPS" and "DEVELOPER"
username is unique
