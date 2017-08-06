import com.typesafe.sbt.packager.Keys.{packageName, defaultLinuxInstallLocation}
import com.typesafe.sbt.packager.docker.Cmd
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._

object Distribution {
  lazy val settings = Seq(
    dockerRepository:= Some("mwizner"),
    dockerBaseImage := "greyfoxit/alpine-openjdk8",
    dockerUpdateLatest := true,
    defaultLinuxInstallLocation in Docker := s"/opt/${packageName.value}",
    dockerExposedVolumes += s"/etc/opt/${packageName.value}",
    dockerCommands ++= Seq(
      Cmd("USER", "root"),
      Cmd("RUN", "apk --no-cache --update add openssl bash"),
      Cmd("RUN", "rm -rf /var/cache/apk/*")
    )
  )
}
