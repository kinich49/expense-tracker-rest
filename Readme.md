# Expense Tracker REST

The back-end codebase of a simple budgeting app.

### Dependencies
Java 11+ and Maven 3.6 are required to build this project, but not for running. You can use Docker to run the project

### How to execute

You can execute the project via maven

```
mvn spring-boot:run -P${environment}
```

Substitute ${environment} with one of the following profiles:
* development
* production


Or if you'd rather use docker, then execute the following

```
docker build --build-arg stage=${environment} --tag expense-tracker-rest:development .

docker run --rm -p ${port}:${port} --name expense-tracker-rest expense-tracker-rest:${environment}
```

Again substitute ${environment} with one of the former profiles, and substitute ${port} with the appropriate port in the 
corresponding properties file according to the environment you selected


## Stack
This project uses,
* Spring Boot as the main framework
* H2 for DB in development and Postgresql for RDBMS in production
* Junit Jupiter for unit testing
* Lombok to reduce boilerplate.

## Contributing
You are free to submit PRs to improve the code, or to open a ticket
to request a feature. 