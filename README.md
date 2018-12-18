# Link Shortener application

## Synopsis

Provides services to generate short urls and redirection using the generated short urls. Some basic statistics services are also provided.
Developed using spring boot / rdbms persistence apache derby db

### Local setup

*   clone repo

```shell
git clone 
```

*  build project and docker images
```shell
$MVN_HOME/bin/mvn -Dspring.profiles.active=test clean verify install jacoco:report dockerfile:build site
```

*  start apache derby db
```shell
docker run -p 1527:1527 --name=docker_apachedb az82/docker-derby
```

*  start spring boot link shortener application
```shell
docker run -p 1527:1527 --name=docker_apachedb az82/docker-derby
```

### API endpoints and Sample usage

*   Swagger Documentation
```shell
http://localhost:8080/swagger-ui.html 
```

*   Request some short link generation
```shell
for url in "www.bbc.co.uk" "www.google.co.uk" "www.rte.ie" "www.cnn.com" "www.youtube.com" "www.facebook.com" "www.baidu.com" "www.yahoo.com" "www.amazon.com" "www.wikipedia.org" "www.qq.com" "www.twitter.com"
do
echo $url
curl -H "Content-Type: text/html" -d $url -X POST http://localhost:8080/short/link
done
```

*   view all processed links
```shell
curl -X GET http://localhost:8080/short/links
```

*   test redirects (link sourced from ehcache), redirect client info persisted to table :  short_links.hit_metrics
```shell
http://localhost:8080/ly/281dab9e
```

*   check hit metrics for link with id
```shell
http://localhost:8080/stats/5
```


### Dev notes

Principal Tech stack components

*   Java 8
*   Ehcache
*   Spring
*   Swagger Api Documentation
*   Spring Data JPA
*   Jacoco Code Coverage
*   Findbugs
*   Spring Boot
*   Apache Derby DB
*   Docker

### Development Documetation

Unit test, Integration test, code coverage and findbugs report are be viewed in document
```shell
target/site/index.html
```
