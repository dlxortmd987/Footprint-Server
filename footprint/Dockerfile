FROM adoptopenjdk/openjdk11

ARG JAR_FILE=build/libs/footprint-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["nohup", "java","-jar","/app.jar", "/server.log", "2>&1", "&"]