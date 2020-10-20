package com.basalt.echo;

import com.basalt.echo.upper.UpperClient;
import io.micronaut.context.annotation.Value;
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
public class EchoController {

  private final Logger logger = LoggerFactory.getLogger(EchoController.class);
  private final EmbeddedServer embeddedServer;
  private final Environment environment;
  private final UpperClient upperClient;
  @Value("${echo.value:value from code}")
  private String configValue;

  public EchoController(Environment environment, EmbeddedServer embeddedServer, UpperClient upperClient) {
    this.environment = environment;
    this.embeddedServer = embeddedServer;
    this.upperClient = upperClient;
  }

  @Get("echo")
  public Echo echo(@QueryValue("value") String value) throws UnknownHostException {
    var upperResponse = upperClient.upper(value);
    var port = embeddedServer.getPort();
    var host = InetAddress.getLocalHost().getHostAddress();
    Echo echo = new Echo()
      .setHost(host)
      .setPort(port)
      .setConfigValue(configValue)
      .setContent(value)
      .setUpperContent(upperResponse.getContent());

    logger.info("echo called, will return " + echo.toString());
    return echo;
  }

}
