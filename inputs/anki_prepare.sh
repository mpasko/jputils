#!/bin/bash
sqlite3.exe < anki_query.sql | sort | uniq > anki_dark_list.txt
