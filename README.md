# Micronaut meets Consul

## Why?

In a micro-service world, we need to find other micro services.
So first of all we need to tell someone, that we are here.
This is done by register ourself to a Service Discovery like Consul.


We use the `micronaut-discovery-client`
```xml
<dependency>
  <groupId>io.micronaut</groupId>
  <artifactId>micronaut-discovery-client</artifactId>
  <scope>compile</scope>
</dependency>
```

## Requirement
### Consul

To be able to connect to Consul we need a Consul instance up and running.
The easiest way it to use the docker image. (https://hub.docker.com/_/consul)
``` 
docker run -d --name=consul --net=host  consul
```

__Do not use the default setting from Consul in production__, this is only the development mode.

Once the Consul container is up and running, you can access the UI via http://localhost:8500/ui/


## Service Discovery
So we can use Consul now as a Service Discovery.

To control the uniqueness, and the name, how the service will end up in the 
Service Discovery list, you can use the configuration in `bootstrap.yml`

```yaml
consul:
  client:
    registration:
      enabled: true
    config:
      format: YAML
    defaultZone: "${CONSUL_HOST:localhost}:${CONSUL_PORT:8500}"
```

## Configuration Service
Consul also provide Key-Value store, which will be automaticall used by String
To add some data in Consul, you can use the UI or the API interface.
```
curl \
    --request PUT \
    --data 'echo:
  value: value from consul' \
    http://127.0.0.1:8500/v1/kv/config/echo
```

here we add some new Key-Value content to __"config/echo"__
Micronaut will use the schema
"config"
"application-name"
to pick up the configuration from Consul

the "data" part is defined in the "bootstrap.yml" as "data-key"
this is not available in micronaut so far, so we have to use the root for this

if you define the consul config in `bootstrap.yml` the values from consul will be
already available on startup, so you can for exampe already define the port of the application.


