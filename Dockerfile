FROM maven:3.6.1-jdk-8 AS MVN_BUILD
MAINTAINER Ana

LABEL version="1.0" \
      description="This is an image for building sample property app"

COPY . /code
RUN echo '{ "allow_root": true }' > rm -Rf /code/target && \
	cd /code/ && \
    mvn clean package
        
FROM openjdk:8-jre
COPY --from=MVN_BUILD /code/target/*.jar /property.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","property.jar"]
