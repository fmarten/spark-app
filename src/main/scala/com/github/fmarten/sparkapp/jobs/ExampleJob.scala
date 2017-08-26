package com.github.fmarten.sparkapp.jobs

import com.github.fmarten.sparkapp.Job

object ExampleJob extends Job {

  override def printHelp(): Unit = {
    println("Usage: example: <arg1> <arg2>")
  }

  override val description: Option[String] = Some("Just prints its two arguments to std.")
  override val command: String = "example"

  override def checkArgs(args: Array[String]): Boolean = args.length >= 2

  override def run(args: Array[String]): Unit = {
    val arg1 = args(0)
    val arg2 = args(1)

    println(s"example: arg1: $arg1, arg2: $arg2")
  }
}
