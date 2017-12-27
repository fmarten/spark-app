package com.github.fmarten.sparkapp.jobs

import com.github.fmarten.sparkapp.SparkJob
import org.apache.spark.sql.SparkSession

object ExampleJob extends SparkJob {

  case class ExampleConfig(arg1: Int = -1, arg2: String = "nothing", arg3: Boolean = false)

  type ConfigType = ExampleConfig
  override val config = ExampleConfig()

  override val command: String = "example"
  override val description = "Just prints its two arguments to std."

  override val parser: Parser = new Parser {
    opt[Int]("arg1").action( (x, c) =>
      c.copy(arg1 = x) ).required().text("first argument")

    opt[String]("arg2").action( (x, c) =>
      c.copy(arg2 = x) ).text("second argument")

    opt[Boolean]("arg3").action( (x, c) =>
      c.copy(arg3 = true) ).text("third option as a flag")
  }

  override def run(spark: SparkSession, config: ExampleConfig): Unit = {
    println(s"$command: arg1: ${config.arg1}, " +
      s"arg2: ${config.arg2}")
  }
}
