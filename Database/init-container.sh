#!/usr/bin/bash

docker build -t sqlserver .
docker run -p 1433:1433 -d sqlserver