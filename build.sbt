import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayJava
name := """style-message"""

version := "0.1.5"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

libraryDependencies += "com.aliyun" % "aliyun-java-sdk-cdn" % "2.0.1" withSources() withJavadoc()

libraryDependencies += "com.aliyun" % "aliyun-java-sdk-core" % "2.3.9" withSources() withJavadoc()

libraryDependencies += "com.aliyun.oss" % "aliyun-sdk-oss" % "2.2.1" withSources() withJavadoc()

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1205-jdbc42" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis" % "3.3.0" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.6" withSources() withJavadoc()

libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "4.0" withSources() withJavadoc()

libraryDependencies += "com.github.mumoshu" %% "play2-memcached-play24" % "0.7.0" withSources() withJavadoc()

libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.0" withSources() withJavadoc()

libraryDependencies += "org.apache.poi" % "poi" % "3.13" withSources() withJavadoc()

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.13" withSources() withJavadoc()

///邮件
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4" withSources() withJavadoc()
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "4.0.0" withSources() withJavadoc()


libraryDependencies += "com.squareup.okhttp" % "okhttp" % "2.7.2"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-kernel_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.1" withSources() withJavadoc()

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.4.1",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)

// 检查代码中使用的过时类细节
javacOptions += "-Xlint:deprecation"
javacOptions += "-Xlint:unchecked"
//  javacOptions ++= Seq("-source", "1.8", "-target", "1.8","-deprecation")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")