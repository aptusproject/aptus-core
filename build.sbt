// aptus-core
//   trying to keep this to a mimimum; TODO: t210125110147 - investigate sbt alternatives, especially https://github.com/com-lihaoyi/mill

// ===========================================================================    
val scala213 = "2.13.4"
val scala212 = "2.12.13"

// ===========================================================================
// settings

lazy val root = (project in file("."))
  .settings(  
    organizationName     := "Aptus Project",
    organization         := "aptusproject",
    name                 := "aptus-core",
    version              := "0.1.0",
	homepage             := Some(url("https://github.com/aptusproject/aptus-core")),
	organizationHomepage := Some(url("https://github.com/aptusproject")),
    startYear            := Some(2021),
    developers           :=
      List(Developer(
		id    = "anthony-cros",
		name  = "Anthony Cros",
		email = "contact.galliaproject@gmail.com",
		url   = url("https://github.com/anthony-cros") )),
	licenses             := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),	
    description          := "Basic utilities for Scala.",
    scalaVersion         :=      scala213,
    crossScalaVersions   := List(scala213, scala212) )

// ---------------------------------------------------------------------------    
scalacOptions      ++= Seq(
    "-encoding", "UTF-8",  
  //"-Yimports:java.lang,scala,scala.Predef,scala.util.chaining" -- not possible for 2.12 it seems (TODO: t210308154253 confirm)
    "-Ywarn-value-discard") ++
  //
  // ---------------------------------------------------------------------------
  (scalaBinaryVersion.value match {
    case "2.13" => Seq("-Ywarn-unused:imports")
    case _      => Seq("-Ywarn-unused-import" ) })

// ===========================================================================    
// dependencies

val compatVersion              = "2.4.1"
val parallelCollectionsVersion = "1.0.0"

// ---------------------------------------------------------------------------
val enumeratumVersion          = "1.5.13" 
val commonsMathVersion         = "3.5"
val commonsLangVersion         = "3.5"
val commonsIoVersion           = "2.8.0"
val commonsCsvVersion          = "1.8"
val commonsCompressVersion     = "1.20"
//val guavaVersion             = "28.0-jre"
val gsonVersion                = "2.8.6"

// ---------------------------------------------------------------------------
libraryDependencies ++= // hard to do anything on the JVM without those nowadays
  Seq(
    "org.scala-lang.modules" %% "scala-collection-compat" % compatVersion, // to support scala <2.13

    // ---------------------------------------------------------------------------
    // misc utils
    "com.beachape"       %% "enumeratum"    % enumeratumVersion ,
    "org.apache.commons" %  "commons-lang3" % commonsLangVersion,
    "org.apache.commons" %  "commons-math3" % commonsMathVersion,
    "commons-io"         %  "commons-io"    % commonsIoVersion  ,

    // compatibility issues (eg https://issues.apache.org/jira/browse/HADOOP-10961)... TODO: t210121165120: shade
    //"com.google.guava" %  "guava"         % guavaVersion     ,

    // ---------------------------------------------------------------------------
    // XSV (tsv, csv, ...), compression (bz2, ...)
    "org.apache.commons" % "commons-csv"      % commonsCsvVersion     ,    
    "org.apache.commons" % "commons-compress" % commonsCompressVersion,

    // ---------------------------------------------------------------------------
    // JSON
    "com.google.code.gson" % "gson" % gsonVersion) ++
  //
  // ---------------------------------------------------------------------------
  (scalaBinaryVersion.value match {
    case "2.13" => Seq("org.scala-lang.modules" %% "scala-parallel-collections" % parallelCollectionsVersion)
    case _      => Seq.empty })

// ===========================================================================
// publishing

publishMavenStyle := true
githubOwner       := "aptusproject"
githubRepository  := "aptus-core"

// ===========================================================================

