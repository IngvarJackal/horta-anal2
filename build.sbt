name := "horta-anal2"

version := "1.0"

mainClass in (Compile, run) := Some("Main")

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "org.json4s" % "json4s-jackson_2.11" % "3.2.11",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)