package com.github.fmarten.sparkapp

import com.github.fmarten.sparkapp.jobs.ExampleJob

object Run extends BaseRun {

  val jobs = List(
    ExampleJob
  )

  override val appName = "sparkapp"

  override val appDescription =
    "A command line tool to run various Apache Spark jobs"
}

