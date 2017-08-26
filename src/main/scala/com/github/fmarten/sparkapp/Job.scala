package com.github.fmarten.sparkapp

trait Job {
  val command: String = this.getClass.getSimpleName
  val description: Option[String] = None
  def printHelp()
  def checkArgs(args: Array[String]): Boolean
  def run(args: Array[String])
}