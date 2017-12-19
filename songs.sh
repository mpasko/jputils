#!/bin/bash

M2_HOME='E:\instalki\apache-maven-3.5.0\'

cd inputs/songs/
./get_all.sh
cd ../..
pwd
env
# weird but it looks like environment is different when runned from w-click
mvn clean install
mvn compile exec:java -Dexec.args=--songs -e
#java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar -D"--songs"
read -p "press enter to continue" dfdfdfd