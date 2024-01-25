FROM eclipse-temurin:17-jdk-alpine

ENV VERSION 1.0.1

WORKDIR /app/VotingSystem/

ADD VotingSystem-$VERSION.jar $VERSION.jar

EXPOSE 9095

ENTRYPOINT ["java","-jar","2.0.2.jar", "--server"]