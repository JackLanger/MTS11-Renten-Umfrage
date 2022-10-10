-- Active: 1647363853103@@localhost@1433
CREATE DATABASE eGameDarling;
GO
-- Create login eGameDarling for tests.
CREATE LOGIN jack
WITH PASSWORD = 'jack',
CHECK_POLICY = OFF,
CHECK_EXPIRATION = OFF;
GO
-- Create user
USE eGameDarling
GO

CREATE USER jack
    FROM LOGIN jack
    WITH DEFAULT_SCHEMA=dbo
    GO
-- set privilage
USE eGameDarling 
        ALTER ROLE db_owner ADD MEMBER jack;