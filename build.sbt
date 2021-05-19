val dottyVersion = "3.0.0-RC3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "viz",
    organization := "net.entelijan",
    version := "0.2-SNAPSHOT",
    // scalacOptions := Seq("-unchecked", "-deprecation")
    scalaVersion := dottyVersion,
  )
