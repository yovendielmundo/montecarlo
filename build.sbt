name := "montecarlo"

scalaVersion := "2.13.1"

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "ch.qos.logback"             % "logback-classic"  % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.2",
  "com.storm-enroute"          %% "scalameter-core" % "0.19",
  "com.storm-enroute"          %% "scalameter"      % "0.19" % Test,
  "org.scalatest"              %% "scalatest"       % "3.0.8" % Test,
  "junit"                      % "junit"            % "4.10" % Test
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
