FROM gradle:8-jdk21-alpine AS build

ARG RUN_INTEGRATION_TESTS=false
ARG BUILD_CACHE_DIR=/home/gradle/.gradle/caches
ARG GRADLE_PROFILE=local

ENV GRADLE_USER_HOME=/home/gradle/.gradle
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.caching=true"
ENV SPRING_PROFILES_ACTIVE=${GRADLE_PROFILE}

COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN if [ "$RUN_INTEGRATION_TESTS" = "true" ]; then \
      ./gradlew build -PrunIntegrationTests --no-daemon; \
    else \
      ./gradlew build -x test --no-daemon; \
    fi

FROM eclipse-temurin:21-jre-alpine

EXPOSE 8000

RUN mkdir /app

COPY --from=build /home/gradle/project/build/libs/library-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
