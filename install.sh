#!/bin/bash

wget --no-parent -nd ftp://ftp.monash.edu.au/pub/nihongo/JMdict_e.gz
gunzip JMdict_e.gz
mv JMdict_e* xml/JMdict.xml

wget --no-parent -nd http://www.edrdg.org/kanjidic/kanjidic2.xml.gz
gunzip kanjidic2.xml.gz
mv kanjidic2.xml xml/kanjidic2.xml

mkdir -p improved_workflow/words/global_sources


