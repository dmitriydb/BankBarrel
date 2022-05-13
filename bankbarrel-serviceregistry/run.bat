docker stop bb-serviceregistry-db
docker rm bb-serviceregistry-db
docker run -d --net=bb-network --name bb-serviceregistry-db -e POSTGRES_USER=postgres -e POSTGRES_DB=serviceregistry -e POSTGRES_PASSWORD=postgres -v /data:/var/lib/postgresql/data -d postgres
docker exec -it bb-serviceregistry-db psql -U postgres -c "CREATE DATABASE serviceregistry;"
docker exec -it bb-serviceregistry-db psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE serviceregistry TO postgres;"
docker stop bb-serviceregistry
docker rm bb-serviceregistry
docker image rm bb-serviceregistry
docker build -t "bb-serviceregistry" .
docker run -i -t --net=bb-network --name bb-serviceregistry -d -p 8181:8181 bb-serviceregistry
docker logs -ft bb-serviceregistry
pause
