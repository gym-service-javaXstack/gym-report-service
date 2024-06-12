FROM openjdk:17-alpine
COPY target/gym-report-service-0.0.1-SNAPSHOT.jar /gym-report-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/gym-report-service-0.0.1-SNAPSHOT.jar"]