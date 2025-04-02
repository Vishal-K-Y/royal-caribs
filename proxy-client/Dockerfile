
FROM openjdk:17

COPY target/proxy-client.jar /proxy-client.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/proxy-client.jar"]