#!/bin/bash

# building projects
cd frontservice && mvn clean install -DskipTests && cd ..
cd gateway && mvn clean install -DskipTests && cd ..
cd noteservice && mvn clean install -DskipTests && cd ..
cd patientservice && mvn clean install -DskipTests && cd ..
cd riskservice && mvn clean install -DskipTests && cd ..

# starting Docker compose
docker-compose up --build
