#!/bin/bash

npm run build

read -p "SSH key path (~/.ssh/netcup_server): " key

rsync -avz --delete -e "ssh -i $key" ./dist/ julian@89.58.7.60:/var/www/algorithms/
