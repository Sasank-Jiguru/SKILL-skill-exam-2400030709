# Production Dockerfile for InsurancePolicy Microservice
# Optimized for fast, lightweight deployment
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

LABEL maintainer="2400030709@kluniversity.in"
LABEL description="Insurance Policy Microservice - Roll No: 2400030709"

COPY target/*.jar app.jar

EXPOSE 2006

ENTRYPOINT ["java", "-jar", "app.jar"]
