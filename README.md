# Kafka Lag Monitor with Kamon and InfluxDB
[![GitHub version](https://img.shields.io/github/tag/mwz/kafka-kamon-lag-monitor.svg?label=release)](https://github.com/mwz/kafka-kamon-lag-monitor/releases)
[![Docker Pulls](https://img.shields.io/docker/pulls/mwizner/kafka-kamon-lag-monitor.svg)](https://hub.docker.com/r/mwizner/kafka-kamon-lag-monitor)

Example Kafka lag monitoring of consumer groups using Kafka AdminClient, Kamon and InfluxDB.

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
- `GROUP_LAG_HISTOGRAM_NAME` - (optional) Name of the consumer group lag histogram, defaults to `kafka-consumer-groups-lag`.
- `GROUP_STATE_HISTOGRAM_NAME` - (optional) Name of the consumer group state histogram, defaults to `kafka-consumer-groups-state`.
- `TICK_INTERVAL` - (optional) Time interval for collecting the metrics, defaults to `10 seconds`.
- `INFLUXDB_HOSTNAME` - Hostname on which InfluxDB is running.
- `INFLUXDB_PORT` - Port on which InfluxDB is listening.
- `EXIT_ON_ERROR` - (optional) Exit with code 1 on any error thrown in the app, defaults to `false`.
  If you're running this in docker, you can use the `--restart unless-stopped` flag to restart the app when it exits.

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
EXIT_ON_ERROR=true
```

## Metrics
The app records Kafka metrics using [Kamon](http://kamon.io/documentation/0.6.x/kamon-core/metrics/instruments/#histograms) histogram instruments - the measurements are exposed in InfluxDB as `kafka-lag-monitor-timers` and tagged with `kafka-consumer-groups-lag` and `kafka-consumer-groups-state` entities. Kafka consumer group states get translated into the following values in the histogram:
- **0** - `Dead` or `Empty`
- **1** - `Stable`
- **2** - `PreparingRebalance` or `CompletingRebalance`
- **3** - any other states

## Changelog
* **1.3.0** - Introduced a new config option to exit the application on error.
* **1.2.0** - Fixed InfluxDB parsing issue & changed the consumer group state metric value to 3 for any unrecognised consumer group states.
* **1.1.0** - Added metrics for consumer group state.
* **1.0.1** - Made `KAFKA_SECURITY_PROTOCOL` environment variable optional.
* **1.0.0** - Forked from [bfil/kafka-kamon-lag-monitor](https://github.com/bfil/kafka-kamon-lag-monitor) and dockerized.
