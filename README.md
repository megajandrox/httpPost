### HttpPost

A simple library to build http requests.

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
Request request = new Request("https://jsonplaceholder.typicode.com/posts/1");
request.addHeader("Accept", "application/json");
request.addQueryParam("userId", "1");
Response response = new SingleRunner(request).execute();
System.out.println(response.getBody());
```
### Patterns used 

#### SingleRunner

This class is used to execute a single request. It is the most basic class to use. 
It is used to execute a request and get the response.

#### MultiRunner

This class is used to execute multiple requests. It is used to execute a request and get the response.    
The requests are executed in parallel.

#### MultiRunnerAsync

This class is used to execute multiple requests. It is used to execute a request and get the response.    
The requests are executed in parallel. The response is returned in a Future.    
The response is returned in a Future.

#### MultiRunnerAsyncWithCallback

This class is used to execute multiple requests. It is used to execute a request and get the response.    
The requests are executed in parallel. The response is returned in a Future.    
The response is returned in a Future.

### Contributing
    
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.    
### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

