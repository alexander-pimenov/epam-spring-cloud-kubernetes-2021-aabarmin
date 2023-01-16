#!/bin/bash

function build_basic() {
  JAR_FILE=$1
  APP_NAME=$2

  docker build -f ./build-scripts/docker/basic/Dockerfile \
    --build-arg JAR_FILE=${JAR_FILE} \
    -t ${APP_NAME}:latest \
    -t ${APP_NAME}:naive .
}

APP_VERSION=0.0.1-SNAPSHOT

# Building the app
cd ..

echo "Building JAR files native"
mvn clean package -DskipTests

echo "Building Docker images native"
build_basic ./config-server/target/config-server-${APP_VERSION}.jar application/config-server
build_basic ./discovery-service/target/discovery-service-${APP_VERSION}.jar application/discovery-service
build_basic ./examinator/target/examinator-${APP_VERSION}.jar application/examinator
build_basic ./mathematic/target/mathematic-${APP_VERSION}.jar application/provider-math
build_basic ./history/target/history-${APP_VERSION}.jar application/provider-history