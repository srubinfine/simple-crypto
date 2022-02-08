### To run docker-compose:
- docker compose up
- docker compose down

### To run swagger UI and test:
- Go to http://localhost:<service-port>/http://localhost:8081/swagger/views/swagger-ui

### To see swagger contracts:
- Go to http://localhost:<service-port>/http://localhost:8081/swagger/views/swagger-ui

### To play with docker Postgresql DB:
When start docker-compose (see above for the command), it'll contain an image of
Postgresql DB. You can connect to it using Adminer tool. 
- Make sure docker compose succeeded by running the following command:
   ##### docker ps
  In the list of running containers, you should see the "postgres" IMAGE and "adminer" IMAGE associated
  running containers
- Go to localhost:8080. Adminer page should come up
- Use the following creds to login into our database (see docker-compose.yml):
  - System: PostgreSQL
  - Server: db
  - Username: root
  - Password: changeme
  - Database: mydb

    
