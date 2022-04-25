FROM maven:3.8.4-jdk-11

MAINTAINER krunets

WORKDIR /hedgehog-app
COPY . .
RUN mvn clean install liquibase:update

CMD cd hedgehog-app \
    mvn spring-boot:run