name := """DeepLearning4j-Studio"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

scalaVersion := "2.12.2"

libraryDependencies += guice

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"

// ---------- DL4J Libs
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-beta4"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-cuda-10.0" % "1.0.0-beta4"
// libraryDependencies += "org.deeplearning4j" % "dl4j-spark_2.11" % "1.0.0-beta3"

// ---------- ND4J Libs
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-beta4"
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.10"

// TODO delete these later
libraryDependencies += "jfree" % "jfreechart" % "1.0.13"
libraryDependencies += "org.jfree" % "jcommon" % "1.0.23"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += evolutions

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
