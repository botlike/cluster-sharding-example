package com.sharding.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.sharding.ShardRegion
import com.sharding.actor.EventHandlerActor.TemperatureEvent

/**
  * Created by cmontecinos on 08-06-17.
  */
class EventHandlerActor extends Actor with ActorLogging{
  var events : List[Double] = List.empty

  override def receive: Receive = {
    case e : TemperatureEvent =>
      update(e.value)
      log.info(s"the average is : {}", events.sum / events.size)
    case _ => unhandled()
  }

  def update(value : Double) = {
    if(events.size == 10) events = events.init :+ value
    else events = events :+ value
  }
}

object EventHandlerActor{
  def props =  Props[EventHandlerActor]
  case class TemperatureEvent(deviceId : String,
                              timestamp : Long,
                              value : Double,
                              clientId : Int)


  val getEntityId: ShardRegion.ExtractEntityId = {
    case s: TemperatureEvent => (s.deviceId, s)
  }

  val getShardId: ShardRegion.ExtractShardId =  {
    case s: TemperatureEvent   => s.clientId.toString
  }

  val shardName = "temperatureShard"
}
