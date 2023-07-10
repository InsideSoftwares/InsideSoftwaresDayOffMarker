#bin/bash

pathLiquibaseBase=$1
tag=$2
properties=$3

cd $pathLiquibaseBase

mvn clean install

mvn liquibase:updateSQL -Dliquibase.propertyFile=$properties -Dliquibase.toTag=$tag -Dliquibase.propertyFileWillOverride=true