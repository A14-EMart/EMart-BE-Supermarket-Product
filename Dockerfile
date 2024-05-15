<<<<<<< HEAD
FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/heymart-auth
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USERNAME=heymart
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USERNAME} && \
    adduser -S -u ${USER_UID} -G ${USERNAME} ${USERNAME}

USER ${USERNAME}
WORKDIR /opt/heymart-auth
EXPOSE 8080
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/heymart-auth/build/libs/*.jar app.jar

ENTRYPOINT [ "java" ]
CMD [ "-jar", "app.jar" ]
=======
FROM gradle:jdk21-alpine
ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

WORKDIR /app
COPY ./backend-sp-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","backend-sp-0.0.1-SNAPSHOT.jar"]
>>>>>>> bac157625da02494b91d6d9fedea7d976b958820
