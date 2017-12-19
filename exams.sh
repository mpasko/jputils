#!/bin/bash

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "
#command="mvn exec:java -Dexec.args=\"--exam $kanji_path`ls $kanji_path`\" -e"

#kanji_path="inputs/darklist_kanji/from_manual2017-10-08T172651604Z.txt"
#words_path="inputs/dicts_filtered/from_manual2017-10-08T165918604Z.txt"
#words_path2="inputs/manual_dic/japanese_summer.txt"
#words_songs1="inputs/dicts_filtered/songs/songs_1.txt"
#words_songs2="inputs/dicts_filtered/songs/songs_2.txt"
#remaining_blacklist="dictionaries/all.txt"
workflow_listening="improved_workflow/words/listening/blacklists/agregate.txt"

$command --exam "$workflow_listening"

read -p "press enter to continue" dfdfdfd