FROM openjdk:17-alpine3.14
COPY build/libs/gateway*.jar /bartap/gateway.jar
WORKDIR /bartap
CMD ["java", "-jar", "Gateway.jar"]
#
## Base image
#FROM alpine:3.13.5 AS base
#
#RUN apk add openjdk16-jre-headless=17.0.3_p7-r2 --no-cache
#
#FROM base AS build
##RUN apk add dos2unix=7.4.2-r0 --no-cache
##
### Entrypoint, tempate properties, app
##COPY scripts/entrypoint.sh scripts/application_template.properties /usr/bartap/
#
#COPY target/gateway*.jar /usr/bartap/gateway.jar
#
##RUN dos2unix /usr/bartap/entrypoint.sh
##RUN chmod +x /usr/bartap/entrypoint.sh
#
#FROM base AS final
#
#COPY --from=build --chown=root:root /usr/bartap /usr/bartap
#CMD ["java", "-jar", "/usr/bartap/gateway.jar"]