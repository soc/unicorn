import sbt._

object Dependencies {

  def mainCore(scalaVersion: String) = Seq(
    if (scalaVersion startsWith "2.11") "com.typesafe.slick" % "slick_2.11" % "2.1.0-M2"
    else "com.typesafe.slick" %% "slick" % "2.1.0-M2",
    "joda-time" % "joda-time" % "2.3",
    "org.joda" % "joda-convert" % "1.6"
  )

  val testCore = Seq(
    "org.scalatest" %% "scalatest" % "2.1.5" % "test",
    "com.h2database" % "h2" % "1.3.175" % "test",
    "ch.qos.logback" % "logback-classic" % "1.0.13" % "test"
  )

  def core(scalaVersion: String) = mainCore(scalaVersion) ++ testCore

  def mainPlay(scalaVersion: String) = Seq(
    if (scalaVersion startsWith "2.11") "com.typesafe.play" % "play-slick_2.11" % "0.8.0-M1"
    else "com.typesafe.play" %% "play-slick" % "0.6.0.1"
  )

  def testPlay(scalaVersion: String) = Seq(
    if (scalaVersion startsWith "2.11") "com.typesafe.play" % "play-test_2.11" % "2.3.1" % "test"
    else "com.typesafe.play" %% "play-test" % "2.2.2" % "test"
  )

  def play(scalaVersion: String) = mainPlay(scalaVersion) ++ testPlay(scalaVersion)

}