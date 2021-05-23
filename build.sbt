lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.0.0",
    name := "viz",
    organization := "net.entelijan",
    version := "0.2-SNAPSHOT",
    // scalacOptions := Seq("-unchecked", "-deprecation")
  )
