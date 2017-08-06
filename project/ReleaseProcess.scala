import com.typesafe.sbt.SbtNativePackager.Docker
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._
import sbtrelease.ReleasePlugin.autoImport._

object ReleaseProcess {
  lazy val default: Seq[ReleaseStep] = Seq(
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepTask(publish in Docker),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
}
