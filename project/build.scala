import sbt._

object AkkaXChange extends Build {
    lazy val akkaXChange = Project(id = "akka-xchange", base = file("akka-xchange"))
}
