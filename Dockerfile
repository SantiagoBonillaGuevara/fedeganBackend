FROM openjdk:17-jdk-slim
COPY target/fedegan-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://ep-orange-firefly-a42dy4oe.us-east-1.aws.neon.tech:5432/neondb
ENV SPRING_DATASOURCE_USERNAME=neondb_owner
ENV SPRING_DATASOURCE_PASSWORD=npg_BplbCUrq6nf4

ENTRYPOINT ["java", "-jar", "app.jar"]