USER="julian"
HOST="89.58.7.60"
REMOTE_DIR="/opt/algorithms/"

mvn clean package -DskipTests

read -p "SSH key path (~/.ssh/netcup_server): " key

rsync -avz -e "ssh -i $key" ./target/algorithm-0.0.1-SNAPSHOT.jar "$USER@$HOST:$REMOTE_DIR"

ssh -i $key "$USER@$HOST" << EOF
cd $REMOTE_DIR
pkill -f algorithms.jar

nohup java -jar algorithms.jar > algorithms.log 2>&1 &
EOF

echo "Spring Boot application started with nohup"