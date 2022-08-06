FROM gradle:jdk17-jammy
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./backend $APP_HOME
  
COPY ./backend/gradlew $APP_HOMEgradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src
    
RUN gradle build -x test || return 0
COPY ./backend .
RUN gradle clean build -x test
    
# actual container
FROM openjdk:17-jdk-alpine
ENV ARTIFACT_NAME=pokerstats-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
    
WORKDIR $APP_HOME
COPY --from=0 $APP_HOMEbuild/libs/$ARTIFACT_NAME .
    
EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}