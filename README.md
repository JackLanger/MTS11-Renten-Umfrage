# MTS11-Renten-Umfrage

Umfrageplattform zum Thema Rente in Deutschland.

![Develop](https://github.com/jaceklangertuda/MTS11-renten-umfrage/actions/workflows/docker-image.yml/badge.svg)
[![Version](https://img.shields.io/badge/powered%20by-Nyx-blue)](https://github.com/mooltiverse/nyx)

Eine Plattform um Umfragen zu erfassen. Ergebnisse können über einen gesicherten REST-Endpoint abgefragt werden.

# Projektbeschreibung

In diesem Projekt werden verschiedene Technologien und Tools eingesetzt:

- **Framework**: [Spring Boot](https://spring.io/) wird als Framework für die Entwicklung von Webanwendungen verwendet.

- **Build-Tool**: [Gradle](https://gradle.org/) fungiert als das Build-Tool, das zur Verwaltung von Projektabhängigkeiten und zur Automatisierung des Build-Prozesses dient.

- **Frontend**: Für das Frontend des Projekts wird [Thymeleaf](https://www.thymeleaf.org/) eingesetzt, um dynamische HTML-Seiten zu erstellen und die Integration von Daten aus der Datenbank in die Benutzeroberfläche zu erleichtern.

- **Datenbankkomponente**: Die [Datenbankkomponente](https://github.com/JacekLangerTUDA/MTS-Umfrage-database) wird in Form eines [Docker](https://docker.com)-Containers bereitgestellt und basiert auf dem [MS-SQL-Server-Container](https://hub.docker.com/_/microsoft-mssql-server). Dies ermöglicht eine einfache Bereitstellung und Skalierbarkeit der Datenbank.

- **Virtualisierung**: Eine Virtualisierung der Anwendung erfolgt in einen Dockercontainer, sodass alle Abhängigkeiten beim Ausrollen enthalten sind. Auf das Einrichten eines Kubernetes-Clusters wird verzichtet, da der Konfigurationsaufwand den Nutzen übersteigt. Die Kommunikation zwischen beiden Containern wird mittels eines Dockernetzwerks gewährleistet.

- **Sicherheit**: Es ist kein Zertifikat zur sicheren Kommunikation (SSL-Verschlüsselung) vorhanden, da keine sensiblen Nutzerdaten erfasst werden. Sollte SSL-Verschlüsselung in der Zukunft gewünscht sein, bietet es sich an, [nginx](https://hub.docker.com/_/nginx) als Reverse Proxy zu verwenden.

Dieses Setup ermöglicht eine effiziente Entwicklung und Bereitstellung der Webanwendung.


# Für Contributor

## Codestyle

Aktuell wird noch kein Codestyle forciert. Es ist empfehlenswert, sich an die [Google-Style-Guide](https://google.github.io/styleguide/javaguide.html) zu halten. Werden vermehrt grobe Verstöße gegen diese Guidelines festgestellt, können diese durchgesetzt werden.

## Technologie Stack

- [SpringBoot](https://spring.io) v2.7.4
- [Hibernate](https://hibernate.org/)
- [Docker](https://docker.com)
- [Gradle](https://gradle.org/) v7.5.1
- [Thymeleaf](https://www.thymeleaf.org/) v3.0.15

## Systemvoraussetzungen

Für die Entwicklung und den Beitrag zum Projekt sind die folgenden Systemvoraussetzungen erforderlich:

- WSL fähige distribution von Windows Linux oder Mac
- Java Development Kit (JDK) Version 8 oder höher.
- [Docker](https://docs.docker.com/get-docker/): Docker muss installiert und korrekt konfiguriert sein.
- [Docker Compose](https://docs.docker.com/compose/install/): Stellen Sie sicher, dass Docker Compose installiert ist, da ein Compose-Skript für das Projekt vorhanden ist.

Sobald Sie die oben genannten Voraussetzungen erfüllen, sind Sie bereit, zur Entwicklung des Projekts beizutragen.
