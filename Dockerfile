FROM gradle:7.5.1-jdk-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean assemble --no-daemon

#FROM eclipse-temurin:11 as jre-build
FROM openjdk:17-bullseye
EXPOSE 8080
USER root
RUN apt update
RUN apt install python3 python3-pip python3-venv dos2unix -y

RUN mkdir /app

ENV PYTHONMEDIA=/bin/venv/media/python
COPY ./DataHandler/ /bin/venv/
RUN mkdir -p $PYTHONMEDIA
COPY --from=build "/home/gradle/src/build/libs/MTS-11-Umfrage-*.*.[0-9].jar" "/app/mts11-umfrage.jar"
# mts11-umfrage.jar
#USER umfrage
RUN for file in /bin/venv/*; do dos2unix $file ; done
RUN chmod +x /bin/venv/python_init
RUN python3 -m pip install --upgrade pip &&\
    pip install virtualenv &&\
    python3 -m venv venv &&\
    . venv/bin/activate &&\
    pip install -r /bin/venv/requirements.txt

RUN /bin/venv/python_init

#ENTRYPOINT ["java", "-jar", "/app/mts11-umfrage.jar"]

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/mts11-umfrage.jar"]
#"-XX:+UseCGroupMemoryLimitForHeap",