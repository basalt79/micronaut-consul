FROM openjdk:14-alpine
COPY target/echo-*.jar echo.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "echo.jar"]