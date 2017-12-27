package com.github.fmarten.sparkapp

import org.apache.spark.sql.SparkSession

abstract class SparkJob extends BaseJob {

  private var sparkConfig = Map[String, String]()

  override def run(config: ConfigType): Unit = {

    val spark = {
      val builder = SparkSession
        .builder()

      for ((key, value) <- sparkConfig) {
        builder.config(key, value)
      }

      builder
        .appName(this.getClass.getSimpleName)
        .getOrCreate()
    }

    run(spark, config)
  }

  def run(spark: SparkSession, config: ConfigType): Unit

  abstract class Parser extends _BaseParser {

    opt[String]("spark-conf").
      valueName("PROP=VALUE").
      text(s"Arbitrary Spark configuration property.").
      validate( x =>
        if (x.contains("=")) success
        else failure(s"Invalid argument to --spark-conf: $x")
      ).
      action { (x, c) =>
        val keyValue = x.split("=", 2) match {
          case Array(k,v) => (k,v) // x.split(_, 2) ensures array is not longer than 2
        }
      sparkConfig = sparkConfig + keyValue
      c
    }
  }

}