package com.github.fmarten.sparkapp.jobs

import com.github.fmarten.sparkapp.SparkJob
import org.apache.spark.sql.SparkSession

object ExampleJob extends SparkJob {

  case class ExampleConfig(opt1: Int = -1, opt2: String = "nothing")

  type ConfigType = ExampleConfig
  override val config = ExampleConfig()

  override val command: String = "example"
  override val description = "Just prints its two option to stdout."

  override val parser: Parser = new Parser {
    opt[Int]("opt1").action((x, c) =>
      c.copy(opt1 = x)).required().text("first option")

    opt[String]("opt2").action((x, c) =>
      c.copy(opt2 = x)).text("second option")

    opt[String]("spark-executor-memory").
      action {(x, c) =>
        additionalSparkConf.set("spark.executor.memory", x)
        c
      }.text("Amount of memory to use per executor process (e.g. 2g, 8g).")
  }

  override def run(spark: SparkSession, config: ExampleConfig): Unit = {
    println(s"$command: opt1: ${config.opt1}, " +
      s"opt2: ${config.opt2}")
  }
}
