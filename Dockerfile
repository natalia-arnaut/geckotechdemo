FROM openjdk:21-jdk

#ARG JAR_FILE

#RUN apk add libgcc

VOLUME /tmp
#COPY target/${JAR_FILE} /app.jar
COPY target/DemoService**.jar app.jar
RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS=""
ENV APPLICATION_OPTS=""
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $APPLICATION_OPTS" ]
