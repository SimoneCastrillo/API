FROM openjdk:17-jdk

WORKDIR /app

COPY target/app-web-0.0.1-SNAPSHOT.jar /app/buffet.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/buffet.jar"]
