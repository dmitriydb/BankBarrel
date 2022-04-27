docker stop bankbarrel-rest
docker rm bankbarrel-rest
docker image rm bankbarrel-rest
docker build -t "bankbarrel-rest" .
docker run -i -t --name bankbarrel-rest -d -p 8887:8887 bankbarrel-rest
docker logs -ft bankbarrel-rest
pause
