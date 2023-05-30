FROM openjdk:19

COPY target/*.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]