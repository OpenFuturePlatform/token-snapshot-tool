#!/bin/bash

# BUILD CHECK
BUILD_JAR_PATH=./build/libs/snapshot-0.0.1-SNAPSHOT.jar
if [ ! -f "$BUILD_JAR_PATH" ]; then
  ./gradlew build
fi

# AUTO COMPLETE
if [ ! -f "snapshot_auto_complete.sh" ]; then
  chmod +x snapshot.sh
  _SNAPSHOT_COMPLETE=bash java -jar "$BUILD_JAR_PATH" >snapshot_auto_complete.sh
  echo complete -F _snapshot ./snapshot.sh >>snapshot_auto_complete.sh
  echo source "$PWD"/snapshot_auto_complete.sh >>~/.bashrc
  echo source "$PWD"/snapshot_auto_complete.sh >>~/.bash_profile
fi

# RUN
java -jar "$BUILD_JAR_PATH" "$@"
