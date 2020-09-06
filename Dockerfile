FROM openjdk:8-jdk-stretch
WORKDIR /srv/corona
COPY target/corona.jar corona.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","corona.jar"]