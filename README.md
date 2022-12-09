# Spring WebClient

Spring WebClient is a non-blocking reactive client which helps to perform HTTP request. They introduced this as part of Spring 5. WebClient API’s are introduced as part of replacing existent Spring RestTemplate 
with these features:

- Supports both synchronous and asynchronous.
- Supports streaming up and down.
- Works with HTTP/1.1
- Supports highly concurrent, reactive, non-blocking with less resource intensive framework.
- Supports both traditional and Spring reactive module.
- Provides a functional API that takes advantage of Java 8 lambdas.

## Setup

The WebClient is part of spring-webflux module and we will add it as required dependency for Spring Reactive support.

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## Creating WebClient

We can use the builder to customize the client behavior. Another option is to create the WebClient by using WebClient.create() and configure it accordingly.

```java
    @Bean
    public WebClient webClient(ClientConfig config) {
        HttpClient httpClient = HttpClient.create()
                .doOnConnected(connection ->  connection
                .addHandlerLast(new ReadTimeoutHandler(10))
                .addHandlerLast(new WriteTimeoutHandler(10)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(config.getUrl())
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "remote.services")
    public ClientConfig clientConfig() {
        return new ClientConfig();
    }
```

```yaml
server:
  port: 9091
remote:
  services:
    url: "http://localhost:9090"
```

## Sending Request

We can use retrieve() and then bodyToFlux() and bodyToMono() method in case we are only interested in the API response. 
We can use the exchange() method in case we need more details from response.

```java
return client.get().uri("/persons/{id}", id)
                   .retrieve()
                   .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new EntityNotFoundException("person not found with id : " + id)))
                   .bodyToMono(Person.class);
```