import sbt._
import sbt.Keys._

object Build extends sbt.Build {


  val project = Project("xtemple", file(".")).settings(
    //sbtPlugin := true,
    organization := "frankbe",
    version := "0.2-SNAPSHOT",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test",
  sourceDirectory in Compile <<= baseDirectory(_ / "src"),
  sourceDirectory in Test <<= baseDirectory(_ / "test"),
  resourceDirectory in Compile <<= baseDirectory(_ / "resources"),
    mainClass in Compile := Some("frankbe.xtemple.Main"),
  scalaVersion := "2.10.1",
    libraryDependencies += "com.github.spullara.mustache.java" % "compiler" % "0.8.11",
    //libraryDependencies += "commons-io" % "commons-io" % "2.4",
    //publishTo := Some(Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)),
    publishMavenStyle := false
    //credentials += Credentials("Artifactory Realm", "scalasbt.artifactoryonline.com", "frankbe", """\{DESkey...==""")
  )

}
