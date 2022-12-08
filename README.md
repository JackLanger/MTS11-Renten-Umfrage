# MTS11-Renten-Umfrage

Umfrageplattform zum Thema Rente in Deutschland.

![Develop](https://github.com/jaceklangertuda/MTS11-renten-umfrage/actions/workflows/docker-image.yml/badge.svg)
[![Version](https://img.shields.io/badge/powered%20by-Nyx-blue)](https://github.com/mooltiverse/nyx)

Eine Plattform um Umfragen durhzuführen und die Ergebnisse mittels eines REST-Endpoints auslesen zu
können.
Es handelt sich dabei um ein [Springboot](https://spring.io/) Projekt
das [Gradle](https://gradle.org/) als build tool verwendet.
Die [Datenbank](https://github.com/JacekLangerTUDA/MTS-Umfrage-database) kommt in form
eines [Docker](https://docker.com)-Containers
und basiert auf dem [MS-SQL-Server-Container](https://hub.docker.com/_/microsoft-mssql-server).
Ein dynamisches HTML design wird durch [Thymeleaf](https://www.thymeleaf.org/) ermöglicht.

## Für Contributor:

### Codestyle

Aktuell wird noch kein Codestyle forciert. Es ist empfehlenswert sich and
die [Google-Style-Guide](https://google.github.io/styleguide/javaguide.html) zu halten.
Werden vermehrt grobe verstöße gegen diese Guidlines festgestellt können diese Durchgesetzt werden.

## Technologie stack:

* [SpringBoot](https://spring.io) v2.7.4
* [Hibernate](https://hibernate.org/)
* [Docker](https://docker.com)
* [Gradle](https://gradle.org/) v7.5.1
* [Thymeleaf](https://www.thymeleaf.org/) v3.0.15

## Systemvoraussetzungen

* WSL fähige distribution von Windows Linux oder Mac
* [Docker und Docker CLI](https://docs.docker.com/desktop/)
* [Docker-Compose](https://docs.docker.com/compose/install/)
