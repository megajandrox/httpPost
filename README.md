### HttpPost

A simple library to build http requests.

### Usage

```java
Request request = RequestBuilder.getInstance()
        .addComponent(new Body("body", "application/json"))
        .addComponent(new Header("header", "value"))
        .addComponent(new QueryParam("queryParam", "value"))
        .build("url", "POST");
```

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

