FROM openjdk:15
EXPOSE 5003

COPY target/rms-assignment-service-*.jar /rms-assignment-service.jar

ENTRYPOINT ["java", "-jar", "/rms-assignment-service.jar"]
