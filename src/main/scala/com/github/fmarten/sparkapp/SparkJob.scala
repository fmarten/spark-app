package com.github.fmarten.sparkapp

import java.util

import org.apache.spark.sql.SparkSession

abstract class SparkJob extends BaseJob {

  private var sparkConfig = Map[String, String]()

  override def run(config: ConfigType): Unit = {

    val spark = {
      val builder = SparkSession
        .builder()

      val e = props.propertyNames()

      while (e.hasMoreElements) {
        val key = e.nextElement.asInstanceOf[String]
        builder.config(key, props.getProperty(key))
      }

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