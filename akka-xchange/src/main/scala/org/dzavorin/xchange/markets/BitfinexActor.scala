package org.dzavorin.xchange.markets

import akka.actor.Props
import info.bitrich.xchangestream.core.{StreamingExchange, StreamingExchangeFactory}
import info.bitrich.xchangestream.bitfinex.BitfinexStreamingExchange

object BitfinexActor {
  def props: Props = Props[BitfinexActor]
}

/**
  * Actor for handling connection and storing latest data from Bitfinex Trade Market
  */
class BitfinexActor extends TradeMarketActor{

  override val exchange = StreamingExchangeFactory.INSTANCE.createExchange(classOf[BitfinexStreamingExchange].getName)

}
