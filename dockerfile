FROM maven:3.9.6-eclipse-temurin-17

LABEL authors=" Aashish Timalsina "

WORKDIR /app

COPY pom.xml .
COPY . /app

RUN mvn package  -DskipTests

CMD ["java", "-jar", "target/w1-1.0-SNAPSHOT.jar"]
