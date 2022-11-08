CREATE DATABASE umfrage;
GO

-- Create login (user_data) for tests.
CREATE LOGIN umfrage
    WITH PASSWORD = 'umfrage',
    CHECK_POLICY = OFF,
    CHECK_EXPIRATION = OFF;

-- Create user_data for tests.
USE umfrage;
CREATE USER [umfrage]
    FROM LOGIN [umfrage]
    WITH DEFAULT_SCHEMA =dbo;

-- Set role to database owner (can do anything).
USE umfrage;
ALTER ROLE db_owner ADD MEMBER [umfrage];
GO

-- Test database

CREATE DATABASE umfrage_test;
GO

-- Create login (user_data) for tests.
CREATE LOGIN test
    WITH PASSWORD = 'test',
    CHECK_POLICY = OFF,
    CHECK_EXPIRATION = OFF;
-- Create user_data for tests.
USE umfrage_test;
CREATE USER [test]
    FROM LOGIN [test]
    WITH DEFAULT_SCHEMA =dbo;

-- Set role to database owner (can do anything).
USE umfrage_test;
ALTER ROLE db_owner ADD MEMBER [test];
GO