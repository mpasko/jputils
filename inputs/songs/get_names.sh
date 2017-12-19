#!/bin/baash

ls *.htm | grep -v "Anime Lyrics" > song_list.txt

cat song_list.txt | cut -d. -f1 | while read song
do
    touch $song.txt
done

