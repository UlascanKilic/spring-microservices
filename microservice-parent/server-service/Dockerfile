FROM openjdk:19

COPY target/*.jar server-service.jar

ENTRYPOINT ["java", "-jar", "/server-service.jar"]