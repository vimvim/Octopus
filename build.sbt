name := "octopus"

version := "1.0-SNAPSHOT"

resolvers += "play-vaadin-integration Snapshots" at "http://henrikerola.github.io/repository/snapshots/"

libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-server" % "7.1.6",
  "com.vaadin" % "vaadin-client-compiled" % "7.1.6",
  "com.vaadin" % "vaadin-themes" % "7.1.6",
  "org.vaadin.playintegration" %% "play-vaadin-integration" % "0.1-SNAPSHOT",
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings

playAssetsDirectories <+= baseDirectory / "VAADIN"