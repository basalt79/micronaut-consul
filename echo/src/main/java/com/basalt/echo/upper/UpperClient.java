package com.basalt.echo.upper;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client(id = "upper")
public interface UpperClient {

  @Get("/upper?value={value}")
  UpperResponse upper(String value);

}

