#!/bin/sh

if [ ! -f $JAVA ]; then
	echo "Can't find java: $JAVA"
	exit -1
fi

# Build classpath (using all jar files in lib directory)
CP=.:properties
for file in libs/*
do
	if [ ! -d $file ]; then
#		file_ext=${file##*.}
#		if [ $file_ext=="jar" ]; then
		if [[ $file == *.jar ]]; then
			CP=$CP:$file
		fi
	fi
done

java -classpath $CP com.frc.main.Application
