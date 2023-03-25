
val CatsVersion             = "2.6.1"
val ScalaCheckVersion       = "1.15.4"
val ScalaTestVersion        = "3.2.9"
val ScalaTestPlusVersion    = "3.2.9.0"
val logbackClassicV = "1.2.3"

inThisBuild(List(
  organization := "io.github.openai4s",
  homepage := Some(url("https://github.com/openai4s/open-api-client")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "paualarco",
      "Pau Alarcón Cerdan",
      "pau.alarcon@gmail.com",
      url("https://github.com/paualarco")
    )
  )
))

ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

lazy val docuGenie = project
  .in(file("docu-genie"))
  .settings(
    name:= "docu-genie",
    scalaVersion := "2.12.14",
    crossScalaVersions := Seq("2.12.14", "2.13.6"),
    Global / onChangedBuildSource := ReloadOnSourceChanges,
    scalacOptions += "-Wconf:any:s",
    // update the g8 template as well
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % "2.12.14",
      "io.github.openai4s" %% "open-api-client" % "0.0.2",
      "ch.qos.logback" % "logback-classic" % logbackClassicV,
      "org.scalatest"     %% "scalatest"        % ScalaTestVersion     % Test,
      "org.scalatestplus" %% "scalacheck-1-15"  % ScalaTestPlusVersion % Test,
      "org.scalacheck"    %% "scalacheck"       % ScalaCheckVersion    % Test
    )
  )


lazy val root = (project in file(".")).aggregate(docuGenie)