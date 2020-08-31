package Tour

import Tour.Helpers._
import com.mongodb.spark._
import org.mongodb.scala.{MongoClient, MongoDatabase}

object MongoAllCollectionsToParquet {
  /**
   * Access to the local MongoDB instance (running on WSL Ubuntu)
   * get the database list and, for each collection of each database,
   * export data on a parquet file
   * given the parquet repo main path $PARQUET_DIR, each collection data is
   * exported in $PARQUET_DIR\[database_name]\[collection_name] dir
   */

  /*
   * WORKAROUND to write Parquet file on Windows
   * https://stackoverflow.com/questions/40764807/null-entry-in-command-string-exception-in-saveastextfile-on-pyspark
   * You are missing winutils.exe a hadoop binary
   * Depending upon x64 bit / x32 bit System download the winutils.exe file & set your
   * hadoop home pointing to it.
   * - Download the file
   * - Create hadoop folder in Your System, ex C:
   * - Create bin folder in hadoop directory, ex : C:\hadoop\bin
   * - paste winutils.exe in bin, ex: C:\hadoop\bin\winuitls.exe
   * - In User Variables in System Properties -> Advance System Settings
   * - Create New Variable Name: HADOOP_HOME Path: C:\hadoop\
*/
  private var PARQUET_DIR: String = "C:\\Users\\cecconial\\Documents\\PARQUET\\"

  def main(args: Array[String]): Unit = {

    val mongoClient: MongoClient = if (args.isEmpty) MongoClient() else MongoClient(args.head)

    // get the names of databases
    val databaseNames = mongoClient.listDatabaseNames().results()
    for (dbName <- databaseNames) {
      println("Found database: " + dbName)
      // get the handle of this database
      val database: MongoDatabase = mongoClient.getDatabase(dbName)
      val collectionNames = database.listCollectionNames().results()

      // export each database collection into parquet
      for (collName <- collectionNames) {
        println("\tFound collection: " + collName)

        import org.apache.spark.sql.SparkSession

        val spark = SparkSession.builder()
          .master("local")
          // .appName("MongoSparkConnectorIntro")
          .config("spark.mongodb.input.uri", "mongodb://localhost:27017/" + dbName + "." + collName)
          .config("spark.mongodb.output.uri", "mongodb://localhost:27017/" + dbName + "." + collName)
          .getOrCreate()

        val genericDF =  MongoSpark.load(spark)
        println("\tRecords: " + genericDF.count)
        // println(genericDF.first.toJson)

        // genericDF.show(false)
        genericDF.printSchema()
        genericDF.write.parquet(PARQUET_DIR + "\\" + dbName + "\\" + collName + ".parquet")
        // Thread.sleep(2000)
        // close Spark Session
        spark.close()
      }
    }

    // release resources
    mongoClient.close()

  }
}
