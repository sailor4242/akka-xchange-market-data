package org.dzavorin.xchange.markets

import akka.actor.Props
import info.bitrich.xchangestream.bitstamp.BitstampStreamingExchange
import info.bitrich.xchangestream.core.StreamingExchangeFactory

object BitstampActor {
  def props: Props = Props[BitstampActor]
}

/**
  * Actor for handling connection and storing latest data from Bitstamp Trade Market
  */
class BitstampActor extends TradeMarketActor {

  override val exchange = StreamingExchangeFactory.INSTANCE.createExchange(classOf[BitstampStreamingExchange].getName)

}
