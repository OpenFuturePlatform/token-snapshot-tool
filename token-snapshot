#!/usr/bin/env bash

if [ -z "$TOKEN_SNAPSHOT_PATH" ]; then
  export TOKEN_SNAPSHOT_PATH=$PWD
fi

# BUILD CHECK
BUILD_JAR_PATH=$TOKEN_SNAPSHOT_PATH/build/libs/snapshot-0.0.1-SNAPSHOT.jar
if [ ! -f "$BUILD_JAR_PATH" ]; then
  "$TOKEN_SNAPSHOT_PATH"/gradlew build
fi

# RUN
java -jar "$BUILD_JAR_PATH" "$@"
