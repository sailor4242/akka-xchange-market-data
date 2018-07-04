package org.dzavorin.xchange.markets

import akka.actor.Props
import info.bitrich.xchangestream.core.StreamingExchangeFactory
import info.bitrich.xchangestream.okcoin.OkCoinStreamingExchange

object OKcoinActor {
  def props: Props = Props[OKcoinActor]
}

/**
  * Actor for handling connection and storing latest data from OKcoin Trade Market
  */
class OKcoinActor extends TradeMarketActor {

  override val exchange = StreamingExchangeFactory.INSTANCE.createExchange(classOf[OkCoinStreamingExchange].getName)


}
