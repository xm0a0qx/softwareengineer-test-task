FROM eclipse-temurin:17-alpine
WORKDIR /opt/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY database.db .
EXPOSE 8080 9090
ENTRYPOINT ["java", "-jar", "app.jar" ]