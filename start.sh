#!/bin/bash
cd `dirname $0`

if [[ ! -d "./logs" ]]; then
mkdir "./logs"
fi

java -Xms200m -Xmx512m  -jar  ddbuswx-0.1.jar --spring.profiles.active=prod  $1 > ./logs/log.txt 2>&1 &

if [[ $? -ne 0 ]]; then
    echo "start failed  "
    echo -e "\033[31m  failed  \033[0m"
else
    echo "start succeed  "
    echo -e "\033[32m succeed \033[0m"
fi