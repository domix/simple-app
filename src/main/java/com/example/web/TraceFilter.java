package com.example.web;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;

@Filter("/**")
public class TraceFilter implements HttpServerFilter {
  private final TraceService traceService;
  private final String serviceName;

  public TraceFilter(TraceService traceService, @Value("${name:service1}") String serviceName) {
    this.serviceName = serviceName;
    this.traceService = traceService;
  }

  @Override
  public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
    return traceService
      .trace(request)
      .switchMap(aBoolean -> chain.proceed(request))
      .doOnNext(
        res ->
          res.getHeaders().add("x-service-name", serviceName)
      );
  }
}
