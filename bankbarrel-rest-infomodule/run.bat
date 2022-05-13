docker stop bb-rest-infomodule
docker rm bb-rest-infomodule
docker image rm bb-rest-infomodule
docker build -t "bb-rest-infomodule" .
docker run --net=bb-network -i -t --name bb-rest-infomodule -d -p 8887:8887 bb-rest-infomodule
docker logs -ft bb-rest-infomodule
pause
