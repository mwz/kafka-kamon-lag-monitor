# Kafka Lag Monitor with Kamon and InfluxDB
Example Kafka lag monitoring of consumer groups using Kafka 0.10.2.0, Kamon and InfluxDB.

**NOTE**: It does not require access to Zookeeper.

*This application is dockerized and the latest image can be found on Docker Hub [mwizner/kafka-kamon-lag-monitor](https://hub.docker.com/r/mwizner/kafka-kamon-lag-monitor).*


## Configuration
The application can be configured using the following environment variables:
- `KAFKA_BOOTSTRAP_SERVERS` - List of Kafka servers used to bootstrap connections to Kafka.
- `KAFKA_SECURITY_PROTOCOL` - Protocol used to communicate with brokers. `PLAINTEXT` is the default value, use `SSL` to communicate with brokers via SSL.
- `MONITOR_CONSUMER_GROUP` - Consumer group the monitor belongs to.
- `MONITOR_CLIENT_ID` - Client id of the monitor.
- `CONSUMER_GROUPS` - Consumer groups which offset will be monitored, e.g. `group1` or `group1,group2,group3`.
- `POLL_INTERVAL` - (optional) Time interval for polling Kafka, defaults to `5 seconds`.
- `TICK_INTERVAL` - (optional) Time interval for collecting the metrics, defaults to `10 seconds`.
- `INFLUXDB_HOSTNAME` - Hostname on which InfluxDB is running.
- `INFLUXDB_PORT` - Port on which InfluxDB is listening.

The following settings are optional and apply only if `KAFKA_SECURITY_PROTOCOL` is set to `SSL`.
- `KAFKA_SSL_PROTOCOL` - The SSL protocol used to generate the SSLContext, e.g. `TLS`.
- `KAFKA_SSL_KEY_PASSWORD` - The password of the private key in the key store file.
- `KAFKA_SSL_KEYSTORE_TYPE` - The file format of the key store file, e.g. `PKCS12`.
- `KAFKA_SSL_KEYSTORE_PATH` - The location of the key store file.
- `KAFKA_SSL_KEYSTORE_PASSWORD` - The password for the key store file.
- `KAFKA_SSL_TRUSTSTORE_TYPE` - The file format of the trust store file, e.g. `JKS`.
- `KAFKA_SSL_TRUSTSTORE_PATH` - The location of the trust store file.
- `KAFKA_SSL_TRUSTSTORE_PASSWORD` - The password for the trust store file.

An example config:
```
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_SECURITY_PROTOCOL=SSL
KAFKA_SSL_PROTOCOL=TLS
KAFKA_SSL_KEY_PASSWORD=password
KAFKA_SSL_KEYSTORE_PATH=/etc/opt/kafka-kamon-lag-monitor/keystore.p12
KAFKA_SSL_KEYSTORE_PASSWORD=password
KAFKA_SSL_KEYSTORE_TYPE=PKCS12
KAFKA_SSL_TRUSTSTORE_PATH=/etc/opt/kafka-kamon-lag-monitor/truststore.jks
KAFKA_SSL_TRUSTSTORE_PASSWORD=password
KAFKA_SSL_TRUSTSTORE_TYPE=JKS
MONITOR_CONSUMER_GROUP=lag-monitor
MONITOR_CLIENT_ID=lag-monitor
CONSUMER_GROUPS=service1,service2
INFLUXDB_HOSTNAME=localhost
INFLUXDB_PORT=8189
```

## Changelog
 * **1.0.0** - Forked from [bfil/kafka-kamon-lag-monitor](https://github.com/bfil/kafka-kamon-lag-monitor) and dockerized.