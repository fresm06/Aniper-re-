#!/bin/bash
APP_HOME="$(cd "$(dirname "$0")" && pwd -P)"
cd "$APP_HOME"
java -Dorg.gradle.appname=gradlew -classpath "./gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
