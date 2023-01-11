#!/bin/bash

sleep 10s
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i /init/sql/create.sql -o /logs/create.log
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i /init/sql/init.sql -o /logs/init.log
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i /init/sql/data.sql -o /logs/data.log