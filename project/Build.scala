import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "foobar"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.18" ,
    "commons-io" % "commons-io" % "2.3",
    "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
    "org.cloudfoundry" % "auto-reconfiguration" % "0.6.6",
    "com.amazonaws" % "aws-java-sdk" % "1.3.11"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Spring Milestone Repository" at "http://maven.springframework.org/milestone"
  )

}
