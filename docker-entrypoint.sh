#!/bin/bash

createQueries=""
grantQueries=""

echo "Schemas:"
IFS=','
read -a schemas <<< "$MYSQL_SCHEMAS"
for schema in "${schemas[@]}"
do
    echo " - $schema"
    createQuery="CREATE DATABASE IF NOT EXISTS \`$schema\`;"
    createQueries="$createQueries$createQuery";
#    grantQuery="GRANT ALL PRIVILEGES ON \`$schema\`.* TO 'jenkins-username'@'%';"
    grantQuery="GRANT ALL PRIVILEGES ON \`$schema\`.* TO $MYSQL_USER@'%' IDENTIFIED BY '$MYSQL_PASSWORD' WITH GRANT OPTION;"
    grantQueryRoot="GRANT ALL PRIVILEGES ON \`$schema\`.* TO root@'%' IDENTIFIED BY '$MYSQL_PASSWORD' WITH GRANT OPTION;"
    grantQueries="$grantQueries$grantQuery$grantQueryRoot"
done

queriesArg="$createQueries$grantQueries"

echo ""
echo "Queries to run:"
IFS=';'
read -a queries <<< "$queriesArg"
for i in "${queries[@]}"
do
    echo " - $i;"
done

mysql -u root -pjenkins-root-password -e "$queriesArg" 2>/dev/null
