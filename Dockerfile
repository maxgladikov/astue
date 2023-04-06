FROM eclipse-temurin:17-jdk-alpine
# VOLUME /tmp
COPY ./build/libs/astue.jar app.jar
CMD ["java", "-jar", "/app.jar"]