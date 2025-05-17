#!/bin/bash

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER dbuser PASSWORD 'dbuser';
    CREATE DATABASE appdb;
    GRANT ALL PRIVILEGES ON DATABASE appdb TO dbuser;
    ALTER DATABASE appdb OWNER TO dbuser;
EOSQL

psql -U dbuser -f /tmp/pracownicy.sql appdb