#!/bin/bash

docker build -t mts11.umfrage.db .

container=$(docker ps | grep mts11.umfrage.db)
volumes=$(docker volume ls | grep umfrage)

# check if volumes exist if not create volumes
if [[ $volumes == "" ]]; then
  docker volume create umfrage-db-data
  docker volume create umfrage-db-backup
fi

# check if container is running if true terminate container
if [[ $container == "" ]]; then
  instance=$(docker ps -aqf "name=umfrage-db" )
  docker kill "$instance"
fi

docker run -p 1433:1433 -d -v umfrage-db-data:/var/opt/mssql/data -v umfrage-db-backup:/var/opt/mssql/backup --name=umfrage-db mts11.umfrage.db