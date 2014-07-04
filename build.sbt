val `unicorn-core` = project
  .settings(Settings.common: _*)
  .settings(
    libraryDependencies ++= Dependencies.core(scalaVersion.value),
    crossScalaVersions := Seq("2.10.4", "2.11.1")
  )

val `unicorn-play` = project
  .settings(Settings.common: _*)
  .settings(
    libraryDependencies ++= Dependencies.core(scalaVersion.value),
    libraryDependencies ++= Dependencies.play(scalaVersion.value)
  )
  .dependsOn(`unicorn-core` % Settings.alsoOnTest)

val unicorn = project
  .in(file("."))
  .aggregate(`unicorn-core`, `unicorn-play`)
  .dependsOn(`unicorn-core`, `unicorn-play`)
  .settings(Settings.core: _*)
  .settings(
    name := "unicorn"
  )
