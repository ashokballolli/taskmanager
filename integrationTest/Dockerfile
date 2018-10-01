
FROM frolvlad/alpine-oraclejdk8:slim
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL

LABEL version="1.0"
LABEL description="Integration Test For Taskmanager App"

#WORKDIR "/app"
#COPY . .

COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app/
RUN mvn clean test
