// aptus-core
//   trying to keep this to a mimimum; TODO: t210125110147 - investigate sbt alternatives, especially https://github.com/com-lihaoyi/mill
//   TODO: t210531095408 - consider offering an alternative version with shaded+pruned dependent libraries

// ===========================================================================
// settings

lazy val root = (project in file("."))
  .settings(  
    organizationName     := "Aptus Project",
    organization         := "io.github.aptusproject", // *must* match groupId for sonatype
    name                 := "aptus-core",
    version              := "0.2.0",
	homepage             := Some(url("https://github.com/aptusproject/aptus-core")),
	organizationHomepage := Some(url("https://github.com/aptusproject")),
    startYear            := Some(2021),
    developers           :=
      List(Developer(
		id    = "anthony-cros",
		name  = "Anthony Cros",
		email = "contact.galliaproject@gmail.com",
		url   = url("https://github.com/anthony-cros") )),
    scmInfo              :=
      Some(ScmInfo(
        browseUrl  = url("https://github.com/aptusproject/aptus-core"),
        connection =     "scm:git@github.com:aptusproject/aptus-core.git")),
	licenses             := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),	
    description          := "Basic utilities for Scala.",
    scalaVersion         :=      "3.0.0",
    crossScalaVersions   := List("3.0.0", "2.13.4", "2.12.13") )

// ---------------------------------------------------------------------------    
scalacOptions ++= Seq("-encoding", "UTF-8") ++ //"-Yimports:java.lang,scala,scala.Predef,scala.util.chaining" -- not possible for 2.12 it seems (TODO: t210308154253 confirm)
  (scalaBinaryVersion.value match {
    case "3"    => Seq("-unchecked")

    case "2.13" => Seq("-Ywarn-value-discard", "-Ywarn-unused:imports,privates,locals")
    case "2.12" => Seq("-Ywarn-value-discard", "-Ywarn-unused-import" ) })

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
val guavaVersion               = "30.1.1-jre"
val gsonVersion                = "2.8.6"

// ---------------------------------------------------------------------------
libraryDependencies ++= // hard to do anything on the JVM without those nowadays
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
    "com.google.code.gson" % "gson" % gsonVersion) ++
  //
  // ---------------------------------------------------------------------------
  (scalaBinaryVersion.value match {
    case "3"    => Seq.empty
    case "2.13" => Seq("org.scala-lang.modules" %% "scala-collection-compat" % compatVersion, "org.scala-lang.modules" %% "scala-parallel-collections" % parallelCollectionsVersion)    
    case "2.12" => Seq("org.scala-lang.modules" %% "scala-collection-compat" % compatVersion) })    

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

libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

// ===========================================================================
// publishing

sonatypeRepository     := "https://s01.oss.sonatype.org/service/local"
sonatypeCredentialHost :=         "s01.oss.sonatype.org"        
publishMavenStyle      := true
publishTo              := sonatypePublishToBundle.value
//pomIncludeRepository := { _ => false }
//credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

// ===========================================================================

