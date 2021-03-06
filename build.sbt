name := "spark-app"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % s"${sparkVersion}_0.7.4" % "test"

// https://github.com/holdenk/spark-testing-base/issues/148#issuecomment-255672739
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % "test"

// https://github.com/holdenk/spark-testing-base#minimum-memory-requirements-and-ooms
fork in Test := true
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")

mainClass in Compile := Some("com.github.fmarten.sparkapp.Run")
