FROM alpine:latest
RUN apk add openjdk8
COPY ss-scrumptious-auth-0.0.1-SNAPSHOT.jar /home/restaurant-auth.jar
ENTRYPOINT java -jar /home/restaurant-auth.jar
