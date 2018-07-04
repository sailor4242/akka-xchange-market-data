package org.dzavorin.xchange

import com.typesafe.config.ConfigFactory

/**
  * Configuration properties from "application.conf"
  */
object AppConfig {

  val config = ConfigFactory.load.getConfig("http")

  val port = config.getInt("port")

  val queueSize = config.getInt("queueSize")
}
