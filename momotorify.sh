#!/usr/bin/env bash
if [ `uname` == "Darwin" ]; then
    echo "make sure you"
    echo "brew install gnu-sed"
elif [ "$(expr substr $(uname -s) 1 5)" == "MINGW" ]; then
    echo "you need to have sed installed, no clue how to do that on windows"
fi

mkdir -p momotor
for f in src/main/java/solver/*.java; do
    sed '1,2d' $f > momotor/`basename $f`
done
