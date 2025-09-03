# SADAD-Integration-App

This project is a full-stack application consisting of:
- **Backend**: Spring Boot (Java 21)
- **Frontend**: Angular (SSR enabled)
- **Database**: Oracle XE (running in Docker)

The application is containerized and orchestrated using **Docker Compose**.

---

## 🚀 Getting Started

### 1. Prerequisites
Make sure you have the following installed on your system:
- [Docker Desktop](https://www.docker.com/products/docker-desktop)  
- [Docker Compose](https://docs.docker.com/compose/)  

---

### 2. Clone the repository
Run the following commands: 

`git clone https://github.com/mokhatib2/sadad-integration-app.git`

`cd sadad-integration-app`

### 3. Build and run the application
Use this command to build and run the application using docker (note: make sure docker is open) : 

`docker compose up --build`

This will:

-Build the Spring Boot backend image
-Build the Angular frontend image
-Start Oracle XE database with required ports exposed

### 4. Seed the database
-Connect to the database on localhost:1521 using your database viewer software 
-Use the sample-data.sql to seed the database



