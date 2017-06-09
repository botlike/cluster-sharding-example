package com.sharding

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by cmontecinos on 06-06-17.
  */
object Main {

  def main(args: Array[String]): Unit = {
    val port = args(0)
    print(port)
    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
      .withFallback(ConfigFactory.load())

    val system = ActorSystem("exampleSystem",config)
  }








}
