#!/usr/bin/env bash

JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
WORKDIR=/opt/lab-gnss/back
LOG_FILE=/var/log/lab-gnss/console-main.log
JAVA_OPTIONS=" -Xms256m -Xmx512m -server "
APP_OPTIONS="--spring.config.location=application.properties"

cd $WORKDIR
"{$JAVA_HOME}/bin/java" $JAVA_OPTIONS -jar lab-gnss-0.0.1-SNAPSHOT.jar $APP_OPTIONS > "{$LOG_FILE}"