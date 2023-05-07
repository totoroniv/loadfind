FROM openjdk:17
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
ENV TZ=Aaia/Seoul
ENTRYPOINT ["java", "-jar", "./app.jar"]