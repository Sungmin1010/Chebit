FROM java:8
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar", "app.jar"]
