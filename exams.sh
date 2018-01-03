#!/bin/bash

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "
#command="mvn exec:java -Dexec.args=\"--exam $kanji_path`ls $kanji_path`\" -e"

workflow_listening="improved_workflow/words/listening/blacklists/from_manual2017-12-28T230848333Z.txt"
workflow_reading="improved_workflow/words/reading/blacklists/from_manual2017-12-29T011421815Z.txt"

$command --exam "$workflow_reading"

read -p "press enter to continue" dfdfdfd