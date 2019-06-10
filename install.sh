#!/bin/bash

function install_dict () {
	base=$1
	target=$2
	name=$3
	wget --no-parent -nd $base/$target -O $target
	gunzip $target --stdout > xml/$name
}

#wget --no-parent -nd ftp://ftp.monash.edu.au/pub/nihongo/JMdict_e.gz
#gunzip JMdict_e.gz
#mv JMdict_e* xml/JMdict.xml

#wget --no-parent -nd http://www.edrdg.org/kanjidic/kanjidic2.xml.gz
#gunzip kanjidic2.xml.gz
#mv kanjidic2.xml xml/kanjidic2.xml

install_dict ftp://ftp.monash.edu.au/pub/nihongo JMdict_e.gz JMdict.xml
install_dict http://www.edrdg.org/kanjidic kanjidic2.xml.gz kanjidic2.xml
install_dict http://ftp.monash.edu/pub/nihongo enamdict.gz enamdict.xml

mkdir -p improved_workflow/words/global_sources

command="java -jar target/jputils-1.0-SNAPSHOT-jar-with-dependencies.jar "

$command --install
