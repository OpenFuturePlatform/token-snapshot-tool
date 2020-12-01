#!/usr/bin/env bash

# BUILD CHECK
BUILD_JAR_PATH=./build/libs/snapshot-0.0.1-SNAPSHOT.jar
if [ ! -f "$BUILD_JAR_PATH" ]; then
  ./gradlew build
fi

# RUN
java -jar "$BUILD_JAR_PATH" "$@"
