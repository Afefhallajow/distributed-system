FROM openjdk:8
EXPOSE 8762
ADD JAR_FILE=target/docker-server.jar docker-server.jar

ENTRYPOINT ["java","-jar","/app.jar"]