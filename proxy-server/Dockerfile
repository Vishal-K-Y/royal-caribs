
FROM openjdk:17

COPY target/proxy-server.jar /proxy-server.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/proxy-server.jar"]