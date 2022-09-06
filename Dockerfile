#FROM anapsix/alpine-java
FROM adoptopenjdk/openjdk11:alpine-slim

RUN addgroup -S appusr && adduser -S appusr -G appusr && mkdir /opt/weather-data-app

COPY target/weather-data-0.0.1-SNAPSHOT.jar /opt/weather-data-app/weatherapp.jar

RUN chown -R appusr /opt/weather-data-app

EXPOSE 8080

ENTRYPOINT ["java", \
"-jar", \
"/opt/weather-data-app/weatherapp.jar", \
"--spring.config.location=file:/opt/config/application.yaml" \
]