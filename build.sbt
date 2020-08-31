name := "SbtScalaMongoSparkPrimer"

version := "0.1"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.2",
  "org.apache.spark" %% "spark-core" % "2.4.2",
  "org.apache.spark" %% "spark-sql" % "2.4.2"
)

libraryDependencies ++= Seq( "org.apache.spark" % "spark-core_2.11" % "2.1.0")

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0"