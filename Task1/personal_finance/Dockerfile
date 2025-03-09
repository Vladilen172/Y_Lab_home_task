FROM openjdk:21-jdk-slim-buster

WORKDIR /app/

COPY target/personal_finance-1.0-SNAPSHOT-jar-with-dependencies.jar /app/personal_finance.jar

ENTRYPOINT ["java", "-jar", "personal_finance.jar"]