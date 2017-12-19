#!/bin/bash

export M2_HOME="/e/instalki/apache-maven-3.5.0/"

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "

mvn compile exec:java -Dexec.args="--workflow" -e
#$command --workflow

read -p "press enter" dfdfdf
