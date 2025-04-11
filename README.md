# Demo Service

## Introduction
Service to store the Recipe.

## Installation

1. Checkout the codebase
2. Copy and configure the environment files for local development: `cp .env.dist .env`
3. Copy and configure the config files:
   - `cp config/application.properties.example config/application.properties`
4. Run the application...
   1. ...in the IDE (run the DB first: `docker-compose up -d db`);
   2. ...in Docker `mvn clean package dockerfile:build` and `docker-compose up -d`
