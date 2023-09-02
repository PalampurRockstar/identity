FROM openjdk:8-jdk-alpine
ENTRYPOINT ["/usr/bin/java", "-jar", "/onboarding-0.0.1-SNAPSHOT.jar"]
ADD target/onboarding-0.0.1-SNAPSHOT.jar  /onboarding-0.0.1-SNAPSHOT.jar
EXPOSE 8080


# FROM maven:3.6.3-jdk-8 AS maven_build
# COPY pom.xml /tmp/
# COPY src /tmp/src/
# WORKDIR /tmp/
# RUN mvn packageThanks krubShall I create Enable Rate limi
# FROM openjdk:8-jdk-alpine
# EXPOSE 8080
# COPY --from=maven_build /tmp/target/onboarding-0.0.1-SNAPSHOT.jar /data/onboarding-0.0.1-SNAPSHOT.jar
# CMD java -jar /data/onboarding-0.0.1-SNAPSHOT.jar