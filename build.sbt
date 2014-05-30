
name := "ploto_ats"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.guava"  % "guava" % "17.0",
  "com.google.inject" % "guice" % "3.0",
  "mysql" % "mysql-connector-java" % "5.1.18"
)

play.Project.playJavaSettings
