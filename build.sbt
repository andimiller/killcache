lazy val CatsEffectVersion = "1.0.0-RC"
lazy val Fs2Version        = "0.10.4"
lazy val Http4sVersion     = "0.18.11"
lazy val RhoVersion        = "0.18.0"
lazy val CirceVersion      = "0.9.2"
lazy val DoobieVersion     = "0.5.3"
lazy val H2Version         = "1.4.196"
lazy val FlywayVersion     = "5.0.5"
lazy val LogbackVersion    = "1.2.3"
lazy val ScalaTestVersion  = "3.0.3"
lazy val ScalaCheckVersion = "1.13.4"
lazy val LettuceVersion    = "5.1.0.M1"
lazy val Fs2ReactiveStreamsVersion = "0.5.1"

lazy val root = (project in file("."))
  .settings(
    organization := "today.urself",
    name := "killcache",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-Ypartial-unification"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel"   %% "cats-effect"         % CatsEffectVersion,
      "co.fs2"          %% "fs2-core"            % Fs2Version,

      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.http4s"      %% "rho-core"            % RhoVersion,
      "org.http4s"      %% "rho-swagger"         % RhoVersion,
      "io.circe"        %% "circe-core"          % CirceVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "io.circe"        %% "circe-java8"         % CirceVersion,

      "com.h2database"  %  "h2"                  % H2Version,
      "org.flywaydb"    %  "flyway-core"         % FlywayVersion,
      "org.tpolecat"    %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"    %% "doobie-postgres"     % DoobieVersion,
      "org.tpolecat"    %% "doobie-h2"           % DoobieVersion,
      "org.tpolecat"    %% "doobie-scalatest"    % DoobieVersion,

      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,

      "io.lettuce"      % "lettuce-core"         % LettuceVersion,

      "com.github.zainab-ali" %% "fs2-reactive-streams" % Fs2ReactiveStreamsVersion,

      "org.scalatest"   %% "scalatest"           % ScalaTestVersion  % Test,
      "org.scalacheck"  %% "scalacheck"          % ScalaCheckVersion % Test,

      "ai.grakn" % "redis-mock" % "0.1.6" % Test
    )
  )

