FROM java:8 

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

COPY pom.xml /code/pom.xml
COPY startup_service.sh /code/startup_service.sh
COPY cleanup_service.sh /code/cleanup_service.sh
COPY src /code/src
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

EXPOSE 4567
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-version"]
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/my-app-1.0.jar"]