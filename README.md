# API-Auth

#### This API will handle all User functions from registration to Roles management and account edition.

#### All the authentication process is done by JWT tokens which are generated and sent to front-end when the user logs in. The token will be validated on every request to every API by an interceptor implemented on the [Auth-Lib](https://github.com/Peralta-CashFlow/CashFlow-Libraries/tree/main/Auth-Lib)

# Summary

- [Documentation](#documentation)
- [Containerization](#containerization)
- [Observability](#observability)
- [Environment Variables](#environment-variables)
- [Sonar Badges](#sonar-badges)

# Documentation

Detailed business documentation, endpoints, and domain rules can be found in the project Wiki.

[👉 Access the full documentation here](https://github.com/Peralta-CashFlow/CashFlow-API-Auth/wiki)

# Containerization

To run the API in a containerized environment, you can use Docker. Access this [DockerHub](https://hub.docker.com/r/viniciusperalta/cashflow-api-auth)
repository that contains the Docker image for this API, you can pull the image and run it.

If you want, clone this [repository](https://github.com/Peralta-CashFlow/CashFlow-Compose) where we have a 
Docker Compose file that will run all CashFlow environment locally! Checkout the documentation on the repository.

# Observability

This API is configured to expose metrics and traces for observability purposes with [Prometheus](https://prometheus.io/)
also on the Docker Compose file mentioned above, it is integrated with [Grafana](https://grafana.com/). 
Reefer to the Organization's documentation for more details about observability.

# Environment Variables

- DATABASE_CONNECTION_STRING;
- DATABASE_USERNAME;
- DATABASE_PASSWORD;
- APP_CROSS_ORIGIN;
- SERVER_PORT;
- JWT_SECRET;
- JWT_EXPIRATION;

# Sonar Badges

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Peralta-CashFlow_CashFlow-API-Auth&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Peralta-CashFlow_CashFlow-API-Auth&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Peralta-CashFlow_CashFlow-API-Auth&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Peralta-CashFlow_CashFlow-API-Auth&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Peralta-CashFlow_CashFlow-API-Auth&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=Peralta-CashFlow_CashFlow-API-Auth)
