package org.dzavorin.xchange.markets

import akka.actor.Props
import info.bitrich.xchangestream.binance.BinanceStreamingExchange
import info.bitrich.xchangestream.core.StreamingExchangeFactory

object BinanceActor {
  def props: Props = Props[BinanceActor]
}

/**
  * Actor for handling connection and storing latest data from Binance Trade Market
  */
class BinanceActor extends TradeMarketActor {

  override val exchange = StreamingExchangeFactory.INSTANCE.createExchange(classOf[BinanceStreamingExchange].getName)
}
