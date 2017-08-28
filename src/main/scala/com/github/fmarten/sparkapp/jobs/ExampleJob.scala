package com.github.fmarten.sparkapp.jobs

import com.github.fmarten.sparkapp.Job

case class ExampleConfig(arg1: Int = -1, arg2: String = "nothing")

class ExampleJob(override val appName: String) extends Job[ExampleConfig] {

  override val command: String = "example"
  override val description: Option[String] = Some("Just prints its two arguments to std.")

  override val config = ExampleConfig()

  override val parser = new Parser {
    opt[Int]("arg1").action( (x, c) =>
      c.copy(arg1 = x) ).required().text("first argument")

    opt[String]("arg2").action( (x, c) =>
      c.copy(arg2 = x) ).text("second argument")
  }

  def run(config: ExampleConfig): Unit = {
    println(s"$command: arg1: ${config.arg1}, " +
      s"arg2: ${config.arg2}")
  }
}
