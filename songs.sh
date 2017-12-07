#!/bin/bash

cd inputs/songs/
./get_all.sh
cd ../..
mvn exec:java -Dexec.args=--songs -e
