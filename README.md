[![Build Status](https://travis-ci.com/rvolosatovs/2IO90.svg?token=Rr1zHeZEE84zs4P7sgSv&branch=master)](https://travis-ci.com/rvolosatovs/2IO90)

# Instructions
If you want to use overleaf git interface, then add a `--recursive` flag during `git clone` of this repo.
To update it, or if something doesn't work, first try:
```
    git submodule update --init --remote --rebase
```

# Vizualizer

* first install matplotlib dependency: https://matplotlib.org/users/installing.html
* to run: `python vizualizer.py`
* after pasting output of algo press control-d on unix systems to initiate process, windows: control-z and then press enter (not sure). This will also force a blank line after input normally.

# Building the project
* Go to root of the repository and do 
```
./gradlew build
```

* To run:
```
java PackingSolver [-s/g] [-f <file>] 
```

By default it uses settings, which do not interfere with Momotor

## Flags
* `-f "test/file/path"` to specify the file to parse
* `-s` for stupid packer
* `-g` for greedy packer
