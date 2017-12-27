package com.github.fmarten.sparkapp

import java.io.{File, FileInputStream}
import java.util.Properties

abstract class BaseJob {
  type ConfigType
  val config: ConfigType
  val props: Properties = new Properties()
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

    opt[File]("properties-file").
      text(s"Path to a file from which to load extra properties. Default: conf/defaults.conf").
      action { (x, c) =>
        props.load(new FileInputStream(x))
        c
      }.
      withFallback(() => new File("conf/defaults.conf"))
  }

}