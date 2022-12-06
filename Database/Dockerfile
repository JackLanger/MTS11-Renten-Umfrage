FROM mcr.microsoft.com/mssql/server:2022-latest AS sqlbase
ENV SA_PASSWORD SuperD3vPass
ENV ACCEPT_EULA Y
ENV MSSQL_PID Express

FROM sqlbase AS server
USER root
RUN apt-get update -y
RUN apt-get full-upgrade -y

FROM server

RUN apt-get install dos2unix

COPY ./init /init/sql/
COPY ./entry /entry


RUN dos2unix /entry/create.sh
RUN dos2unix /entry/entrypoint.sh
RUN chmod +x /entry/create.sh
RUN chmod +x /entry/entrypoint.sh

USER mssql
RUN /opt/mssql/bin/sqlservr & /entry/create.sh

ENTRYPOINT ["/entry/entrypoint.sh"]