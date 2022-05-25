docker stop bb-webapp-db
docker rm bb-webapp-db
docker run -d --net=bb-network --name bb-webapp-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v /data:/var/lib/postgresql/data -d postgres
docker exec -it bb-webapp-db psql -U postgres -c "CREATE DATABASE bbwebapp WITH OWNER 'postgres' ENCODING 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' template = 'template0';"
docker exec -it bb-webapp-db psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE bbwebapp TO postgres;"
docker stop bb-webapp
docker rm bb-webapp
docker image rm bb-webapp
docker build -t "bb-webapp" .
docker run --net=bb-network -i -t --name bb-webapp -d -p 8888:8888 bb-webapp
docker logs -ft bb-webapp
pause
