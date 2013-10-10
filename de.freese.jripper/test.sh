#!/bin/sh

CDPARANOIA="$(which cdparanoia)"
if [ $? != 0 ]; then
	echo "cdparanoia not installed"
    exit 5
elif [ ! -x $CDPARANOIA ]; then
	echo "cdparanoia not executable"
	exit 5;   
fi

$CDPARANOIA -w -B -d /dev/dvd 1

#$SHELL
