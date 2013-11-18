import sbt._
import Keys._
import play.Project._


/*
"org.apache.wicket" % "wicket-core" % wicketVersion,
    "org.apache.wicket" % "wicket-spring" % wicketVersion,
    "org.apache.wicket" % "wicket-auth-roles" % wicketVersion,
    "org.springframework" % "spring-context" % springVersion,
    "org.springframework" % "spring-context-support" % springVersion,
    "org.springframework" % "spring-webmvc" % springVersion,
    "org.springframework" % "spring-web" % springVersion,
    "org.springframework" % "spring-orm" % springVersion,
    "org.springframework" % "spring-test" % springVersion,
    "org.springframework.security" % "spring-security-core" % springSecurityVersion,
    "org.springframework.security" % "spring-security-web" % springSecurityVersion,
    "org.springframework.security" % "spring-security-config" % springSecurityVersion,
    "org.springframework.security" % "spring-security-taglibs" % springSecurityVersion,
    "org.hibernate" % "hibernate-core" % hibernateVersion ,
    "org.hibernate" % "hibernate-entitymanager" % hibernateVersion ,
    "org.hibernate" % "hibernate-c3p0" % hibernateVersion ,
    "junit" % "junit" % "4.8.1" % "test",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    "commons-fileupload" % "commons-fileupload" % "1.2.2" ,
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
    "javax.transaction" % "jta" % "1.1" % "provided",
    "org.slf4j" % "slf4j-api" % "1.7.2",
    "org.slf4j" % "slf4j-log4j12" % "1.7.2",
    "mysql" % "mysql-connector-java" % "5.1.23",
    "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.5",
    "de.agilecoders.wicket" % "bootstrap" % "0.7.6",
    "com.novocode" % "junit-interface" % "0.10-M2" % "test"
 */

object ApplicationBuild extends Build {

  val appName         = "Octopus"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    javaJpa,
    javaJdbc,
    jdbc,
    "mysql" % "mysql-connector-java" % "5.1.18",
    anorm,
    "org.mockito" % "mockito-all" % "1.9.0",
    "org.springframework" % "spring-context" % "3.2.3.RELEASE",
    "org.springframework" % "spring-context" % "3.2.4.RELEASE",
    "org.springframework" % "spring-beans" % "3.2.4.RELEASE",
    "org.springframework" % "spring-aspects" % "3.2.4.RELEASE",
    "org.springframework.data" % "spring-data-jpa" % "1.4.2.RELEASE",
    "org.springframework.scala" % "spring-scala" % "1.0.0.M2",
    "org.hibernate" % "hibernate-core" % "4.2.7.SP1" ,
    "org.hibernate" % "hibernate-entitymanager" % "4.2.7.SP1" ,
    "org.hibernate" % "hibernate-c3p0" % "4.2.7.SP1" ,
    "javax.transaction" % "jta" % "1.1" % "provided",
    "commons-dbcp" % "commons-dbcp" % "1.4",
    "org.codehaus.jackson" % "jackson-mapper-asl" % "1.8.5"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Keys.javaOptions in (Runtime) +=
    //  "-javaagent:/Users/vim/.ivy2/cache/org.springframework/spring-instrument/jars/spring-instrument-3.2.2.RELEASE.jar"
    // Add your own project settings here
  )

}
