FROM openjdk:17
VOLUME /tmp
EXPOSE 4444

COPY properties.json properties.json
COPY target/*-jar-with-dependencies.jar app.jar
RUN mkdir -p chromedriver
COPY chromedriver/* chromedriver/

ENTRYPOINT ["java","-Xms1G","-Xmx2G","-jar","/app.jar"]
