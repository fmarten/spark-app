package com.github.fmarten.sparkapp

import scopt.OptionDef

abstract class Job[C] {

  val appName: String

  val command: String = this.getClass.getSimpleName
  val description: Option[String] = None

  val config: C
  val parser: Parser
  def run(config: C): Unit

  def main(args: Array[String]): Unit = {
    run(parser.parse(args, config).get)
  }

  def printHelp(): Unit = {
    parser.showHeader()
  }

  def checkArgs(args: Array[String]): Boolean = parser.parse(args, config).nonEmpty

  abstract class Parser extends scopt.OptionParser[C](appName + " " + command) {
    override val showUsageOnError = false

    help("help").text("prints this usage text")
  }

}