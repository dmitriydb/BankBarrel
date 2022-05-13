docker stop bb-webapp
docker rm bb-webapp
docker image rm bb-webapp
docker build -t "bb-webapp" .
docker run --net=bb-network -i -t --name bb-webapp -d -p 8888:8888 bb-webapp
docker logs -ft bb-webapp
pause
