name := "dialogflow-client"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.google.cloud" % "google-cloud-dialogflow" % "0.59.0-alpha",
  "com.typesafe" % "config" % "1.3.2",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)
