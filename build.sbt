name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.mohiva" %% "play-silhouette" % "3.0.4",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24",	// Add bootstrap3 helpers and field constructors (http://play-bootstrap3.herokuapp.com/)
  "com.typesafe.play" %% "play-mailer" % "3.0.1",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "net.ceedubs" %% "ficus" % "1.1.2",
  //Webjars
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "requirejs" % "2.2.0"
)


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"
