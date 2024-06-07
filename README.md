# URL Shortener

This application converts long URLs into shorter, more manageable ones.

## Table of Contents

- [Installation](#installation)
- [Endpoints](#endpoints)
  - [Create a shortlink](#create-a-shortlink)
  - [Redirect](#redirect)
- [Swagger](#swagger)
- [JMeter](#jmeter)


## Installation
To install and run the application locally:

1. Clone the repository:

   ```sh
   git clone https://github.com/f-boscaino/url-shortener.git
   cd url-shortener
   ```
2. Make sure Docker is running
3. Run the docker-compose file:
   ```sh
   docker-compose up
   ```
4. (Optional) Deploy the application to K8s
   ```sh
   cd k8s
   minikube start
   ```

## Endpoints

### Create a shortlink

```sh
curl --location 'localhost:8080/add' \
    --header 'Content-Type: application/json' \
    --data '{
        "url": "http://www.google.com"
    }'
```
### Redirect   
```sh
  curl --location 'localhost:8080/6b86c2db'
```

## Swagger
This application has a Swagger UI available at http://localhost:8080/swagger-ui/index.html

## JMeter
This application has a JMeter test plan: https://github.com/f-boscaino/url-shortener/blob/master/src/test/jmeter/Url%20Shortener.jmx
