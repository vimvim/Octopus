name := "octopus"

version := "1.0-SNAPSHOT"

resolvers += "play-vaadin-integration Snapshots" at "http://henrikerola.github.io/repository/snapshots/"

resolvers ++= Seq(
  "release-springsource" at "http://repo.springsource.org/release/",
  "milestone-springframework" at "http://maven.springframework.org/milestone/",
  "vaadin-addons" at "http://maven.vaadin.com/vaadin-addons"
)

libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-server" % "7.1.6",
  "com.vaadin" % "vaadin-client-compiled" % "7.1.6",
  "com.vaadin" % "vaadin-themes" % "7.1.6",
  "com.vaadin.addon" % "jpacontainer" % "3.1.1",
  "org.vaadin.playintegration" %% "play-vaadin-integration" % "0.1-SNAPSHOT",
  jdbc,
  anorm,
  cache
)     

javaOptions in run ++= Seq("-javaagent:lib/weaver/aspectjweaver.jar")

play.Project.playScalaSettings

playAssetsDirectories <+= baseDirectory / "VAADIN"