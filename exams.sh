#!/bin/bash

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "
#mvn exec:java -Dexec.args="--exam $kanji_path`ls $kanji_path`" -e

kanji_path="inputs/darklist_kanji/from_manual2017-10-08T172651604Z.txt"
words_path="inputs/dicts_filtered/from_manual2017-10-08T165918604Z.txt"
words_path2="inputs/manual_dic/japanese_summer.txt"
words_songs1="inputs/dicts_filtered/songs/songs_1.txt"
words_songs2="inputs/dicts_filtered/songs/songs_2.txt"
remaining_blacklist="dictionaries/all.txt"
#$command --exam "$kanji_path"
#$command --exam "$words_path"
#$command --exam "$words_path2"
$command --exam "$words_songs1"
$command --exam "$words_songs2"

#todo Press any key...