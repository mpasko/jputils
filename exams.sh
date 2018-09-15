#!/bin/bash

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "
#command="mvn exec:java -Dexec.args=\"--exam $kanji_path`ls $kanji_path`\" -e"

workflow_listening1="improved_workflow/words/listening/yousei_teikoku_blacklist_list.txt"
workflow_reading1="improved_workflow/words/reading/yousei_teikoku_blacklist_read.txt"

$command --exam "$workflow_listening1"
$command --exam "$workflow_reading1"

read -p "press enter to continue" dfdfdfd
