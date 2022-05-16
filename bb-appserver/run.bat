docker stop bb-appserver-db
docker rm bb-appserver-db
docker run -d --net=bb-network --name bb-appserver-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v /data:/var/lib/postgresql/data -d postgres
docker exec -it bb-appserver-db psql -U postgres -c "CREATE DATABASE bblogic WITH OWNER 'postgres' ENCODING 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' template = 'template0';"
docker exec -it bb-appserver-db psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE bblogic TO postgres;"
docker stop bb-appserver
docker rm bb-appserver
docker image rm bb-appserver
docker build -t "bb-appserver" .
docker run  --net=bb-network -it -p 8886:8886 --name bb-appserver -d bb-appserver
docker logs -ft bb-appserver
pause
