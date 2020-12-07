FROM openjdk:8-jre-buster
LABEL maintainer="liu.zhao@inspur.com"
WORKDIR /server
ADD ./app-0.1.0.jar .
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar /server/app-0.1.0.jar" ]
