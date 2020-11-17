FROM maven:3.6.1-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.4_11
COPY --from=build /workspace/target/*.jar app-ui.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app-ui.jar"]