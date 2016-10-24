#!/bin/bash

mvn install;

mkdir $1;
echo "Created folder"

cp target/gendoman-jar-with-dependencies.jar $1/gendoman.jar;
echo "java -jar gendoman.jar \$*" > $1/gendoman;
chmod +x $1/gendoman;
echo "Copied files"

echo "GenDOMan installed in $1"

