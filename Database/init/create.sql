CREATE DATABASE umfrage;
GO
-- Create login umfrage for tests.
CREATE LOGIN umfrage
WITH PASSWORD = 'umfrage',
CHECK_POLICY = OFF,
CHECK_EXPIRATION = OFF;
GO
-- Create user
USE eGameDarling
GO

CREATE USER umfrage
    FROM LOGIN umfrage
    WITH DEFAULT_SCHEMA=dbo
    GO
-- set privilage
USE umfrage
        ALTER ROLE db_owner ADD MEMBER umfrage;