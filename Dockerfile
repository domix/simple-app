FROM openjdk:14-alpine
COPY build/libs/simple-app-*-all.jar simple-app.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "simple-app.jar"]