#!/bin/bash

for num in `seq -w 1 42`; do
	wget -r --no-parent http://www.goethe-verlag.com/book2/_VOCAB/EN/ENZH/$num.HTM
done

for num in `seq -w 3 102`; do
    wget -r --no-parent http://www.goethe-verlag.com/book2/IT/ITZH/ITZH$num.HTM
done