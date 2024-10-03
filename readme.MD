# Banking Management System

## Overview
The Banking Management System is a microservice designed to facilitate essential banking operations.
This microservice is responsible for:
* Onboarding users & admins
* Roles and permissions
* Authentication
* Account registration process and transfer payments between user accounts in the system, managed by the administrator.

## Tech Stack
This project utilizes the following technologies:

- **Build Tool**: Maven (version >= 3.9.5)
- **Language**: Java 21
- **Framework**: Spring Boot (version >= 3.3.0)
- **Data Access**: Spring Data JPA
- **Security**: Spring Security
- **DBMS**: MySQL

## Prerequisites
Before running the Banking Management System, ensure that you have the following installed:

- **Java SDK**: Version 21 
- **MySQL Server**: An operational MySQL server instance for data storage and management

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`

## Docker guideline
### Build docker image
`docker build -t <account>/banking-system:1.0.0 .`
### Push docker image to Docker Hub
`docker image push <account>/banking-system:1.0.0`
### Create network:
`docker network create trongdev-network`
### Start MySQL in trongdev-network
`docker run --network trongdev-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.39-debian`
### Run your application in trongdev-network
`docker run --name banking-system --network trongdev-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/banking banking-system:1.0.0`

## Install Docker on ubuntu

# Add Docker's official GPG key:
```bash
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

# Add the repository to Apt sources:
```bash
echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
$(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo docker run hello-world
```

# Install Redis
```bash
curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list
sudo apt-get update
sudo apt-get install redis
sudo service redis-server start
redis-cli
127.0.0.1:6379> ping
PONG
```