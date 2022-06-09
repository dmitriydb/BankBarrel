psql -U postgres -c "CREATE DATABASE bblogic WITH OWNER 'postgres' ENCODING 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' template = 'template0';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE bblogic TO postgres;"
