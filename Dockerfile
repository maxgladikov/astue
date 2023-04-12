FROM eclipse-temurin:17-jdk-alpine 
WORKDIR /app
# VOLUME /tmp
#RUN mkdir /tmp
#COPY /build/libs/astue.jar ./app.jar
COPY astue.jar ./app.jar
CMD ["java", "-jar", "/app/app.jar" ]