import sbt._
import sbt.Keys._

object Build extends sbt.Build {

  val project = Project("xtemple", file(".")).settings(
    organization := "frankbe",
    version := "0.3.1-SNAPSHOT",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.2" % "test",
    sourceDirectory in Compile <<= baseDirectory(_ / "src"),
    sourceDirectory in Test <<= baseDirectory(_ / "test"),
    resourceDirectory in Compile <<= baseDirectory(_ / "resources"),
    scalaVersion := "2.10.3",
    libraryDependencies += "com.github.spullara.mustache.java" % "compiler" % "0.8.14"
    //sbtPlugin := true,
    //mainClass in Compile := Some("frankbe.xtemple.Main"),
    //scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    //publishTo := Some(Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)),
    //publishMavenStyle := false
    //credentials += Credentials("Artifactory Realm", "scalasbt.artifactoryonline.com", "frankbe", """\{DESkey...==""")
  )

}
