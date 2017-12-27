package com.github.fmarten.sparkapp

abstract class BaseJob {
  type ConfigType
  val config: ConfigType
  val appName: String = "sparkapp"

  // Remove trailing $ from companion objects, .i.e. from MyObject$
  val command: String = this.getClass.getSimpleName.replace('$',' ').trim
  val description: String
  protected val parser: _BaseParser
  def run(config: ConfigType): Unit

  def main(args: Array[String]): Unit = {
    run(parser.parse(args, config).get)
  }

  def printHelp(): Unit = {
    parser.showHeader()
  }

  def checkArgs(args: Array[String]): Boolean = parser.parse(args, config).nonEmpty

  abstract class _BaseParser extends scopt.OptionParser[ConfigType](appName + " " + command) {
    override val showUsageOnError = false
    head(" ")
    note(s"$description\n")
    note("Options:")

    help("help").text("prints this usage text")
  }

}