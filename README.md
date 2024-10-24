### HttpPost

A simple library to build http requests.

[![Java CI with Maven](https://github.com/megajandrox/httpPost/actions/workflows/maven.yml/badge.svg)](https://github.com/megajandrox/httpPost/actions/workflows/maven.yml)

### Requirements

* Java 8    
* Maven
* IntelliJ IDEA
* Git
* GitHub
* [JsonPlaceholder](https://jsonplaceholder.typicode.com/)

### Installation

```bash
git clone https://github.com/megajandrox/httpPost
cd HttpPost
mvn clean install
```
### Usage

```java
Request request = new Request("https://jsonplaceholder.typicode.com/users/1", "GET");
Response httpResponse = new SingleRunner(request).execute();
System.out.println(httpResponse.getBody());
```
### Patterns used 

#### SingleRunner

This class is used to execute a single request. It is the most basic class to use. 
It is used to execute a request and get the httpResponse.

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

