package com.github.fmarten.sparkapp

import com.github.fmarten.sparkapp.jobs.ExampleJob

object Run {

  val jobs = List(ExampleJob)

  def main(args: Array[String]): Unit = {
    args.headOption match {
      case None => printHelp()
      case Some(command) => runCommand(command, args.drop(1))
    }
  }

  def runCommand(command: String, args: Array[String]): Unit = {
    jobs.map(job => job.command -> job).toMap.get(command) match {
      case None => println(s"sparkapp: '$command' is not a command.")
      case Some(job) => if (job.checkArgs(args)) job.run(args) else job.printHelp()
    }
  }

  def printHelp(): Unit = {
    println("Usage: sparkapp COMMAND\n")
    println("A command line tool to run various Apache Spark jobs.\n") // TODO

    println("Commands:")
    val maxLength = jobs.map(_.command.length).max
    jobs.foreach { j =>
      println(s" ${j.command.padTo(maxLength, " ")}   ${j.description}")
    }
  }
}
