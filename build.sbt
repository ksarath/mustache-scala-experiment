val scalateV            = "1.9.6"
val commonsLangV        = "3.9"
val mustacheV           = "0.9.6"

val baseDeps = Seq(
  "com.github.spullara.mustache.java" %  "compiler"                   % mustacheV,
  "org.scalatra.scalate"              %% "scalate-core"               % scalateV,
  "org.apache.commons"                %  "commons-lang3"              % commonsLangV,
  "com.github.pureconfig"             %% "pureconfig"                 % "0.12.1",
  "com.typesafe.scala-logging"        %% "scala-logging"              % "3.9.2"       exclude("org.slf4j", "slf4j-api"),
  "org.slf4j"                         %  "slf4j-api"                  % "1.7.26",
  "org.scalatest"                     %% "scalatest"                  % "3.0.8"       % "test",
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)

publishTo := Some("Artifactory Realm" at "https://artifactory.ing.net/artifactory/releases_mvn_contactingAPI/")

publishMavenStyle := true

lazy val jenkinsCredentials =
  Seq(
    Path("/shared") / "jenkins" / ".sbt" / ".credentials",
    Path(sys.env.getOrElse("WORKSPACE", Path.userHome.toString)) / ".sbt" / ".credentials"
  ).collect {
    case path if path.canRead => Credentials(path)
  }

credentials ++= jenkinsCredentials

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    organization := "nl.ing.api.contacting",
    name := "scalate-example",
    maintainer := "twilion@ing.com",
    licenses := Seq(
      "MIT" -> url(
        s"https://gitlab.ing.net/Twilio/contacting-cassandra-util/blob/v${version.value}/LICENSE"
      )
    ),
    scalaVersion := "2.12.10",
    scalacOptions := Seq(
      "-feature",
      "-deprecation",
      "-explaintypes",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-language:higherKinds",
      "-language:existentials",
      "-Xfatal-warnings",
      "-Xlint:-infer-any,_",
      "-Ywarn-value-discard",
      "-Ywarn-numeric-widen",
      "-Ywarn-extra-implicit",
      "-Ywarn-unused:_"
    ) ++ (if (isSnapshot.value) Seq.empty
    else
      Seq(
        "-opt:l:inline"
      )),
    libraryDependencies ++= baseDeps ++ Seq(
      // plugins
      compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
      compilerPlugin(
        ("org.typelevel" % "kind-projector" % "0.11.0").cross(CrossVersion.full)
      )
    )
  )

