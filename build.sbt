import AssemblyKeys._

// ---------------------------------------------------------------------------------------------------------------------
//
// assembly setup
//
// ---------------------------------------------------------------------------------------------------------------------
assemblySettings

name := "simplehttp"

organization := "com.simplehttp"

version := "0.2.6"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.4", "2.11.2")

scalacOptions ++= Seq("-optimize", "-Yclosure-elim", "-Yinline-warnings", "-Ywarn-adapted-args", "-Ywarn-inaccessible", "-feature", "-deprecation")

//scalacOptions in doc ++= Seq("--doc-root-content", "rootdoc.txt")

// ---------------------------------------------------------------------------------------------------------------------
//
// build info setup
//
// ---------------------------------------------------------------------------------------------------------------------
buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)

buildInfoPackage := "com.simplehttp"

// ---------------------------------------------------------------------------------------------------------------------
//
// dependencies
//
// ---------------------------------------------------------------------------------------------------------------------

libraryDependencies ++= Seq()

libraryDependencies += ("org.msgpack" %% "msgpack-scala" % "0.6.11").exclude("org.scala-lang", "scala-compiler").exclude("org.scala-lang", "scalap")

libraryDependencies += ("com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2") //.exclude("org.scala-lang", "scala-reflect")

libraryDependencies += ("log4j" % "log4j" % "1.2.17")

libraryDependencies += ("org.slf4j" % "slf4j-log4j12" % "1.7.7")

libraryDependencies += ("org.scalatest" %% "scalatest" % "2.1.7" % "test")

libraryDependencies += ("org.simpleframework" % "simple" % "5.1.6")

libraryDependencies += ("com.github.scopt" %% "scopt" % "3.2.0")
