docker stop bb-mom-monitoring-webapp
docker rm bb-mom-monitoring-webapp
docker run --net=bb-network -d --name bb-mom-monitoring-webapp -p 8162:8161 webcenter/activemq
docker stop bb-monitoring-webapp
docker rm bb-monitoring-webapp
docker image rm bb-monitoring-webapp
docker build -t "bb-monitoring-webapp" .
docker run --net=bb-network -i -t --name bb-monitoring-webapp -d bb-monitoring-webapp
docker logs -ft bb-monitoring-webapp
pause
