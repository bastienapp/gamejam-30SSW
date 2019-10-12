#!/usr/bin/env bash
clear
javac -cp src/main/java -d out src/main/java/Main.java
cp src/main/resources/* out
java -cp out Main