name := "worldpay-stream"

version := "0.1"

scalaVersion := "2.13.5"

val AkkaVersion = "2.6.8"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.2",
  "org.beanio" % "beanio" % "2.1.0"
)
