name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true