#!/bin/bash

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "

mvn exec:java -Dexec.args="--dictionary" -e
#$command --dictionary
