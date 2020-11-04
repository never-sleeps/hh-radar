FROM maven:3.6.3

ENV PROJECT_DIR=/docker
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn install

FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/docker
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/library* $PROJECT_DIR/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/docker/hh-radar.jar"]