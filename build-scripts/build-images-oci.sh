#!/bin/bash

# Building the app
cd ..

echo "Building JAR and Image files with mvn Spring Boot Maven plugin"
mvn clean package spring-boot:build-image -DskipTests