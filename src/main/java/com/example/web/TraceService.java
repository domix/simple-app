package com.example.web;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

import static io.reactivex.schedulers.Schedulers.io;
import static java.lang.String.format;
import static java.lang.String.join;

@Singleton
public class TraceService {

  private static final Logger LOG = LoggerFactory.getLogger(TraceService.class);
  private final String serviceName;

  public TraceService(@Value("${name:service1}") String serviceName) {
    this.serviceName = serviceName;
  }

  Flowable<Boolean> trace(HttpRequest<?> request) {
    return Flowable.fromCallable(() -> {
      StringBuilder sb = new StringBuilder();

      String path = request.getUri().getPath();
      String name = request.getMethod().name();
      sb.append("\n");
      sb.append("====================== ServiceName ======================\n");
      sb.append(format("%s\n", serviceName));

      sb.append("======================== Cookies ========================\n");
      request.getCookies().forEach((s, cookie)
        -> sb.append(format("Cookie: %s=%s\n", s, cookie.getValue())));

      sb.append("======================== Headers ========================\n");
      request.getHeaders().forEach((s, header)
        -> sb.append(format("%s=%s\n", s, join(", ", header))));

      sb.append("======================== Request ========================\n");
      sb.append(format("uri: %s\n", path));
      sb.append(format("method: %s\n", name));
      sb.append("=========================================================\n");
      LOG.info(sb.toString());
      return true;
    }).subscribeOn(io());
  }
}
