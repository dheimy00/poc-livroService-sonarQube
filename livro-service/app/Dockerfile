FROM openjdk:11-jdk
EXPOSE 8081
VOLUME /tmp
ADD ../build/libs/*-SNAPSHOT.jar livro-service.jar
ENTRYPOINT ["java","-jar","livro-service.jar"]