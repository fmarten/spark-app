package com.github.fmarten.sparkapp.jobs

import com.github.fmarten.sparkapp.Job
import org.apache.spark.sql.SparkSession

class ExampleJob(override val appName: String) extends Job {

  case class ExampleConfig(arg1: Int = -1, arg2: String = "nothing")

  type Config = ExampleConfig
  override val config = ExampleConfig()

  override val command: String = "example"
  override val description = "Just prints its two arguments to std."

  override val parser = new Parser {
    opt[Int]("arg1").action( (x, c) =>
      c.copy(arg1 = x) ).required().text("first argument")

    opt[String]("arg2").action( (x, c) =>
      c.copy(arg2 = x) ).text("second argument")
  }

  def run(config: ExampleConfig): Unit = {
    val session = SparkSession
      .builder()
      .getOrCreate()

    runSpark(config, session)
  }

  def runSpark(config: ExampleConfig, session: SparkSession): Unit = {
    println(s"$command: arg1: ${config.arg1}, " +
      s"arg2: ${config.arg2}")
  }
}
