#!/bin/sh

export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/pft
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=2105

mvn clean install