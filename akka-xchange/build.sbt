import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

name := "akka-xchange"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val akkaHttpVersion = "10.1.3"
lazy val akkaVersion = "2.5.13"
lazy val xChangeVersion = "4.3.2"
lazy val xChangeStreamVersion = "4.3.2"
lazy val scalaTestVersion = "3.0.1"

// a somehow workaround to get build done
// from https://github.com/sbt/sbt/issues/3618
val workaround = {
  sys.props += "packaging.type" -> "jar"
  ()
}

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-http"             % akkaHttpVersion,
  "com.typesafe.akka"           %% "akka-http-xml"         % akkaHttpVersion,
  "com.typesafe.akka"           %% "akka-stream"           % akkaVersion,
  "com.typesafe.akka"           %% "akka-actor"            % akkaVersion,
  "com.typesafe.akka"           %% "akka-remote"           % akkaVersion,
  "com.typesafe.akka"           %% "akka-cluster"          % akkaVersion,
  "com.typesafe.akka"           %% "akka-cluster-tools"    % akkaVersion,
  "com.typesafe.akka"           %% "akka-cluster-sharding" % akkaVersion,

  "com.typesafe.akka"           %% "akka-http-testkit"     % akkaHttpVersion  % Test,
  "com.typesafe.akka"           %% "akka-testkit"          % akkaVersion      % Test,
  "com.typesafe.akka"           %% "akka-stream-testkit"   % akkaVersion      % Test,
  "org.scalatest"               %% "scalatest"             % scalaTestVersion % Test,

  "info.bitrich.xchange-stream" % "xchange-stream-core"    % xChangeStreamVersion,
  "info.bitrich.xchange-stream" % "xchange-bitstamp"       % xChangeStreamVersion,
  "info.bitrich.xchange-stream" % "xchange-binance"        % xChangeStreamVersion,
  "info.bitrich.xchange-stream" % "xchange-bitfinex"       % xChangeStreamVersion,
  "info.bitrich.xchange-stream" % "xchange-okcoin"         % xChangeStreamVersion,
  "info.bitrich.xchange-stream" % "xchange-bitmex"         % xChangeStreamVersion,
  "com.google.code.gson"        % "gson"                   % "2.8.5"
)

enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("org.dzavorin.xchange.App")

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"

dockerBaseImage := "anapsix/alpine-java"
dockerExposedPorts ++= Seq(8080)