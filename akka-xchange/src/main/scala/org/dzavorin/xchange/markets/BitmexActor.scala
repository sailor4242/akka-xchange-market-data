package org.dzavorin.xchange.markets

import akka.actor.Props
import info.bitrich.xchangestream.bitmex.BitmexStreamingExchange
import info.bitrich.xchangestream.core.StreamingExchangeFactory

object BitmexActor {
  def props: Props = Props[BitmexActor]
}

/**
  * Actor for handling connection and storing latest data from BitMEX Trade Market
  */
class BitmexActor extends TradeMarketActor {

  override val exchange = StreamingExchangeFactory.INSTANCE.createExchange(classOf[BitmexStreamingExchange].getName)

}
