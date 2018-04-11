import sbtrelease.Version.Bump.Minor

name := "kafka-kamon-lag-monitor"
scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  "io.kamon" %% "kamon-core" % "0.6.7",
  "io.kamon" %% "kamon-influxdb" % "0.6.8",
  "org.apache.kafka" %% "kafka" % "1.1.0" exclude("log4j", "log4j") exclude("org.slf4j","slf4j-log4j12"),
  "org.slf4j" % "log4j-over-slf4j" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

releaseVersionBump := Minor
releaseProcess := ReleaseProcess.default

Distribution.settings
enablePlugins(JavaServerAppPackaging, DockerPlugin)
