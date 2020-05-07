package com.example.web;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.Map;

@Controller("/**")
public class IndexController {
  private final String serviceName;

  public IndexController(@Value("${name:service1}") String serviceName) {
    this.serviceName = serviceName;
  }

  @Get
  public Map get(HttpRequest request) {

    return Map.of("service", serviceName,
      "uri", request.getUri().getPath(),
      "method", request.getMethod().name());
  }

  @Post
  public Map post(HttpRequest request) {
    return Map.of("service", serviceName,
      "uri", request.getUri().getPath(),
      "method", request.getMethod().name());
  }
}
