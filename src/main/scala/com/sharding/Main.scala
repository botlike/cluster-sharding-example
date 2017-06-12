package com.sharding

import java.util.Date
import akka.actor.ActorSystem
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import com.sharding.actor.EventHandlerActor
import com.sharding.actor.EventHandlerActor.TemperatureEvent
import com.typesafe.config.ConfigFactory
import scala.io.Source

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

    val shardedEventHandler = ClusterSharding(system).start(
      typeName = EventHandlerActor.shardName,
      entityProps = EventHandlerActor.props,
      settings = ClusterShardingSettings(system),
      extractEntityId = EventHandlerActor.getEntityId,
      extractShardId = EventHandlerActor.getShardId)

    if(args.isDefinedAt(1) && args(1) == "input"){
      for(ln <- Source.stdin.getLines()){
        val input = ln.split(":")
        val id = input(0)
        val value = input(1).toDouble
        val clientId = input(2).toInt
        shardedEventHandler ! TemperatureEvent(id,new Date().getTime,value, clientId)
      }
    }
  }

}