import java.nio.file.Path
import sbt.KeyRanks
import sbt.Keys.{libraryDependencies, publishMavenStyle}
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import sbtrelease.ReleasePlugin.autoImport.releaseCrossBuild

ThisBuild / organization := "$organization$"

val projectName = "$name;format="camel"$"
val username            = "$user_name;format="norm"$"
val scalaThirteen       = "2.13.3"
val defaultScalaVersion = scalaThirteen

name := projectName

organization := s"com.github.\$username"

enablePlugins(GhpagesPlugin)
enablePlugins(ParadoxPlugin)
enablePlugins(SiteScaladocPlugin)
enablePlugins(ParadoxMaterialThemePlugin) // see https://jonas.github.io/paradox-material-theme/getting-started.html

ThisBuild / scalaVersion := defaultScalaVersion
val scalaVersions = Seq(scalaThirteen)
ThisBuild / crossScalaVersions := scalaVersions 

paradoxProperties += ("project.url" -> s"https://\$username.github.io/\$projectName/docs/current/")

val scalacSettings = List(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:reflectiveCalls", // Allow reflective calls
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked",
  "-language:reflectiveCalls", // Allow reflective calls
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-Xfuture" // Turn on future language features.
)

val commonSettings: Seq[Def.Setting[_]] = Seq(
  //version := parentProject.settings.ver.value,
  organization := s"com.github.\${username}",
  scalaVersion := defaultScalaVersion,
  resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  autoAPIMappings := true,
  exportJars := false,
  crossScalaVersions := scalaVersions,
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  scalacOptions ++= scalacSettings,
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := s"\${projectName}.build",
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case str if str.contains("application.conf") => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

val $name;format="camel"$Project = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .withoutSuffixFor(JVMPlatform)
  .in(file("."))
  .jvmSettings(commonSettings: _*)
  .jvmSettings(
    publishMavenStyle := true,
    releaseCrossBuild := true,
    coverageMinimum := 90,
    coverageFailOnMinimum := true
  )
  .jvmSettings()
  .settings(
    libraryDependencies ++= List(
      "com.lihaoyi" %%% "scalatags" % "$scalatags_version$",
      "org.scalatest" %%% "scalatest" % "$scalatest_version$" % "test",
      "io.monix" %%% "monix-reactive" % "$monix_version$",
      "io.monix" %%% "monix-eval" % "$monix_version$"
      // "org.typelevel" %%% "cats-core" % "2.0.0"
    )
  )
  .jsSettings(
    libraryDependencies ++= List(
      "org.scala-js" %%% "scalajs-java-time" % "$scalajstime_version$"
      //"com.lihaoyi" %%% "scalarx" % "0.4.0"
    ))

lazy val $name;format="camel"$JVM = $name;format="camel"$Project.jvm
lazy val $name;format="camel"$JS = $name;format="camel"$Project.js

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(SiteScaladocPlugin)
  .enablePlugins(ParadoxPlugin)
  .aggregate($name;format="camel"$JVM, $name;format="camel"$JS)
  .settings(
    publish := {},
    publishLocal := {}
  )

Compile / paradoxMaterialTheme ~= {
  _.withLanguage(java.util.Locale.ENGLISH)
    .withColor("blue", "grey")
    //.withLogoIcon("cloud")
    .withRepository(uri(s"https://github.com/\$username/\$projectName"))
    .withSocial(uri("https://github.com/\$username"))
    .withoutSearch()
}

siteSourceDirectory := target.value / "paradox" / "site" / "main"

siteSubdirName in SiteScaladoc := "api/latest"

git.remoteRepo := s"git@github.com:\$username/$name;format="camel"$.git"
ghpagesNoJekyll := true
releasePublishArtifactsAction := PgpKeys.publishSigned.value
publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)

test in assembly := {}
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

//credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

lazy val makePage =
  taskKey[Unit]("Puts the javascript and html resources together")
    .withRank(KeyRanks.APlusTask)

makePage := {
  import eie.io._
  val jsArtifacts = {
    val path: Path = (fullOptJS in ($name;format="camel"$JS, Compile)).value.data.asPath
    val dependencyFiles =
      path.getParent.find(_.fileName.endsWith("-jsdeps.js")).toList
    path :: dependencyFiles
  }
  val jsResourceDir = (resourceDirectory in ($name;format="camel"$JS, Compile)).value.toPath

  val targetDir = (baseDirectory.value / "target" / "page").toPath.mkDirs()

  val sharedResourceDir =
    (baseDirectory.value / "$name;format="camel"$" / "shared" / "src" / "main" / "resources").toPath

  val sharedJsLibs = sharedResourceDir.children

  // always copy/clobber these
  val clobberFiles = (jsResourceDir.children ++ jsArtifacts).map { file =>
    val to = targetDir.resolve(file.fileName)
    sLog.value.info(s"""cp \${file.fileName} to \$to""".stripMargin)
    (file.toFile, to.toFile)
  }
  IO.copy(clobberFiles, CopyOptions().withOverwrite(true))

  // copy these on demand
  val onDemand = (sharedJsLibs).map { file =>
    val tgt = targetDir.resolve(file.fileName)
    (file.toFile, tgt.toFile)
  }
  IO.copy(onDemand, CopyOptions().withOverwrite(false))
}

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "$package;format=package$.build"

// see http://scalameta.org/scalafmt/
scalafmtOnCompile in ThisBuild := true
scalafmtVersion in ThisBuild := "1.4.0"

// see http://www.scalatest.org/user_guide/using_scalatest_with_sbt
testOptions in Test += (Tests.Argument(TestFrameworks.ScalaTest, "-h", s"target/scalatest-reports", "-oN"))

pomExtra := {
  <url>https://github.com/{username}/{projectName}</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <developers>
      <developer>
        <id>{username}</id>
        <name>{username}</name>
        <url>http://github.com/{username}</url>
      </developer>
    </developers>
}
