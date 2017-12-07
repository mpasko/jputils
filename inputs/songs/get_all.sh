#!/bin/bash

list=`cat folderlist.txt`
for folder in $list
do

   cd $folder
   pwd
   bash ../get_names.sh
   cd ..
done

bash get_names.sh

read -p "Press enter" dfdfd