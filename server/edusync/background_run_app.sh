#!/bin/bash

# Build the project
./gradlew bootJar -x test

# Run the app.jar file in the /build/libs directory
nohup java -jar build/libs/app.jar --spring.profiles.active=server &>/dev/null & disown