# SbtScalaMongoSparkPrimer
Rispetto al progetto SbtScalaMongoPrimer, effettua il processing con Spark.
Target: sviluppare con Scala attraverso IDE IntelliJ IDEA, installata su Windows 10, applicazioni che fanno accesso a MongoDB e Spark, entrambi dockerizzate 
e disponibili su WSL 2 Ubuntu.

**Step 1: Configurazione MongoDB Spark Connector
Fare riferimento al seguente link:
https://docs.mongodb.com/spark-connector/master/
che consente di utilizzare il connettore all’interno del progetto IntelliJ semplicemente creando le seguenti dependency nel file build.sbt:

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

**Step 2: Esempio di applicazione Scala Spark che accede a tutti i db MongoDB su localhost (processo mongod su WSL 2 Ubuntu) e, per ciascun db e per ciascuna collection 
in esso contenuta,  stampa il numero di documents e, acquisendo il contenuto della collection su DataFrame, ne esporta i dati su file Parquet su path locale configurabile.

Un buon punto di partenza è il seguente:
https://www.pavanpkulkarni.com/blog/16-spark-mongo-data-processing/
