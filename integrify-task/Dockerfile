FROM maven:3-openjdk-17-slim AS maven
WORKDIR /app
COPY pom.xml .
RUN #mvn -e -B dependency:resolve
COPY src ./src
RUN mvn clean package -DskipTests=true


FROM openjdk:17-jdk-alpine
COPY --from=maven /app/target/*.jar app.jar

EXPOSE 9000
ENTRYPOINT ["java", "-jar app.jar"]