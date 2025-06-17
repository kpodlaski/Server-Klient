#!/bin/bash

javac -classpath ".:./postgresql-42.7.5.jar" eu/vctl/wfis/PgSQLClient.java
java -classpath ".:./postgresql-42.7.5.jar" eu.vctl.wfis.PgSQLClient

