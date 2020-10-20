package com.basalt.upper;

import io.micronaut.context.env.Environment;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.runtime.server.EmbeddedServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class UpperController {

  private final Logger logger = LoggerFactory.getLogger(UpperController.class);

  private final EmbeddedServer embeddedServer;
  private final Environment environment;

  public UpperController(Environment environment, EmbeddedServer embeddedServer) {
    this.environment = environment;
    this.embeddedServer = embeddedServer;
  }

  @Get("upper")
  public Upper upper(@QueryValue("value") String value) throws UnknownHostException {
    var port = embeddedServer.getPort();
    var host = InetAddress.getLocalHost().getHostAddress();
    Upper upper = new Upper()
      .setHost(host)
      .setPort(port)
      .setContent(value.toUpperCase());
    logger.info("upper called, will return " + upper.toString());
    return upper;
  }

}
