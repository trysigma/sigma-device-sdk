#!/usr/bin/env sh
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Determine the location of the gradle wrapper jar file
WRAPPER_DIR="$(cd "$(dirname "$0")/gradle/wrapper" && pwd)"
java -jar "$WRAPPER_DIR/gradle-wrapper.jar" "$@"
