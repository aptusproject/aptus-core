// aptus-core
//   trying to keep this to a mimimum; TODO: t210125110147 - investigate sbt alternatives, especially https://github.com/com-lihaoyi/mill
//   TODO: t210531095408 - consider offering an alternative version with shaded+pruned dependent libraries

// ===========================================================================
// settings

ThisBuild / organizationName     := "Aptus Project"
ThisBuild / organization         := "io.github.aptusproject" // *must* match groupId for sonatype
ThisBuild / version              := "0.8.0-SNAPSHOT"
ThisBuild / homepage             := Some(url("https://github.com/aptusproject/aptus-core"))
ThisBuild / organizationHomepage := Some(url("https://github.com/aptusproject"))
ThisBuild / startYear            := Some(2021)
ThisBuild / developers           :=
  List(Developer(
	id    = "anthony-cros",
	name  = "Anthony Cros",
	email = "contact.galliaproject@gmail.com",
	url   = url("https://github.com/anthony-cros")))
ThisBuild / scmInfo              :=
  Some(ScmInfo(
    browseUrl  = url("https://github.com/aptusproject/aptus-core"),
    connection =     "scm:git@github.com:aptusproject/aptus-core.git"))
ThisBuild / licenses             := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / description          := "Basic utilities for Scala."
ThisBuild / scalaVersion         :=                         "2.13.16"
ThisBuild / crossScalaVersions   := List("3.3.4" /* LTS */, "2.13.16")

// ===========================================================================
lazy val core = (project in file("core"))
  .settings(
    name   := "aptus-core", // no data/dyn code, just basic utilities
    target := baseDirectory.value / ".." / "bin" / "code" /* TODO: t240103170440 - still leaves a reflect/target folder somehow */)

// ---------------------------------------------------------------------------
lazy val reflect = (project in file("reflect"))
  .settings(
    name   := "aptus-reflect", // e.g WTT/TypeNode
    target := baseDirectory.value / ".." / "bin" / "reflect")
  .dependsOn(core)

// ---------------------------------------------------------------------------
lazy val meta = (project in file("meta"))
  .settings(
    name   := "aptus-meta", // e.g Cls, Fld, ...
    target := baseDirectory.value / ".." / "bin" / "meta")
  .dependsOn(reflect)

// ---------------------------------------------------------------------------
lazy val data = (project in file("data"))
  .settings(
    name   := "aptus-data", // e.g. Dyn, ...
    target := baseDirectory.value / ".." / "bin" / "data")
  .dependsOn(meta)

// ---------------------------------------------------------------------------
lazy val root = (project in file("."))
  .settings(name := "aptus-root") // TODO: t240209192100 - where is this name used?
  .aggregate(core, reflect, meta, data)
  .dependsOn(core, reflect, meta, data)

// ===========================================================================
// scalac options

ThisBuild / scalacOptions ++= Seq("-encoding", "UTF-8") ++
  (scalaBinaryVersion.value match {
    case "3"    => Seq(
      "-unchecked",
      "-no-indent" /* see https://youtu.be/DzjvFx5YYik?si=9WpwITuTKbfH6NYy&t=568 from ScalaIO for rationale */)
    case "2.13" => Seq("-Ywarn-value-discard", "-Ywarn-unused:imports,privates,locals") })

// ===========================================================================    
// dependencies

val compatVersion              = "2.11.0"
val parallelCollectionsVersion = "1.0.4"

// ---------------------------------------------------------------------------
val commonsLangVersion         = "3.14.0"
val commonsMathVersion         = "3.6.1"
val commonsIoVersion           = "2.15.1"
val commonsCsvVersion          = "1.10.0"
val commonsCompressVersion     = "1.26.0"
val gsonVersion                = "2.10.1"
val guavaVersion               = "32.0.1-jre" // careful updating this (often causes issues with Apache Spark)

val uTestVersion               = "0.8.5"

// ---------------------------------------------------------------------------
ThisBuild / libraryDependencies ++= // hard to do anything on the JVM without those nowadays
  Seq(
    // misc utils
    "org.apache.commons"    %  "commons-lang3" % commonsLangVersion,
    "org.apache.commons"    %  "commons-math3" % commonsMathVersion,
               "commons-io" %  "commons-io"    % commonsIoVersion  ,
    
    "com.google.guava" % "guava" % guavaVersion
       exclude("org.codehaus.mojo"       , "animal-sniffer-annotations") // no such sniffing necessary here           
       exclude("org.checkerframework"    , "checker-qual"              )
       exclude("com.google.code.findbugs", "jsr305"                    )
       exclude("com.google.errorprone"   , "error_prone_annotations"   )
       exclude("com.google.guava"        , "failureaccess"             )
       exclude("com.google.guava"        , "listenablefuture"          )
       exclude("com.google.j2objc"       , "j2objc-annotations"        ),

    // ---------------------------------------------------------------------------
    // XSV (tsv, csv, ...), compression (bz2, ...)
    "org.apache.commons" % "commons-csv"      % commonsCsvVersion     ,    
    "org.apache.commons" % "commons-compress" % commonsCompressVersion,

    // ---------------------------------------------------------------------------
    // JSON
    "com.google.code.gson" % "gson" % gsonVersion, // TODO: t230623160248 - switch to ujson rather
    
    // ---------------------------------------------------------------------------    
    "org.scala-lang.modules" %% "scala-collection-compat" % compatVersion) ++
  // ---------------------------------------------------------------------------
  (scalaBinaryVersion.value match {
    case "3"    => Seq("org.scala-lang.modules" %% "scala-parallel-collections" % parallelCollectionsVersion) // if causes issues (eg with Spark), use: .exclude("org.scala-lang.modules", "scala-parallel-collections_3")
    case "2.13" => Seq("org.scala-lang.modules" %% "scala-parallel-collections" % parallelCollectionsVersion) }) ++
  (scalaBinaryVersion.value match {
    case "3"    => Seq.empty
    case "2.13" => Seq("org.scala-lang" %  "scala-reflect" % scalaVersion.value) /* for scala.reflect.runtime.universe */ })

// ===========================================================================
// shading; guava - compatibility issues (eg https://issues.apache.org/jira/browse/HADOOP-10961)... TODO: t210121165120: shade
/*
enablePlugins(ShadingPlugin)
shadedModules += "com.google.guava" % "guava"
shadingRules  += ShadingRule.moveUnder("com.google.common", "guava28")
// TODO: getting: [error] Unrecognized: aptus/, ...
*/

// ===========================================================================
// testing

ThisBuild / testFrameworks      += new TestFramework("utest.runner.Framework")
ThisBuild / libraryDependencies += "com.lihaoyi" %% "utest" % uTestVersion % "test"

// ===========================================================================
// publishing

sonatypeRepository     := "https://s01.oss.sonatype.org/service/local"
sonatypeCredentialHost :=         "s01.oss.sonatype.org"        
publishMavenStyle      := true
publishTo              := sonatypePublishToBundle.value

// ===========================================================================
// assembly (uberjar); run: sbt +assembly

ThisBuild / assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _                             => MergeStrategy.first }

// ===========================================================================

