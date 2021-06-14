Overview
========
This reece-backend is developed using the spring-boot v2.5.1. It requires Java 8 to build and run. It provides the below rest-endpoints. It is configured to listen on port 8080 and its context path is defined as /reece-backend. When it starts up, seeding records will be automatically pre-populated for showcasing purpose. 

a. POST http://localhost:8080/api/addressBooks/ - to create new address book with customer contacts

b. POST http://localhost:8080/api/addressBooks/{id}/contacts/ - to create new customer contacts in existing address book

c. DELETE http://localhost:8080/api/addressBooks/{id}/contacts/ - to delete existing customer contacts in existing address book.

d. GET http://localhost:8080/api/addressBooks/{id} - to print customer contacts in existing address book.

e. POST http://localhost:8080/api/addressBooks/batchInsert - to batch insert customer contacts in multiple address books

f. DELETE http://localhost:8080/api/addressBooks/batchDelete - to batch delete customer contacts in multiple address books

Steps to set up the project
===========================
1. mvn clean install
2. java -jar target/reece-backend-0.0.1-SNAPSHOT.jar


Steps to build & run docker image
===========================
1. docker build . -t reece-backend:0.0.1
2. docker run -p 8080:8080 reece-backend:0.0.1