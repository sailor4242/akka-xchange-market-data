package org.dzavorin.xchange.markets

import io.reactivex.functions.Consumer
import org.dzavorin.xchange.utils.LimitedQueue
import org.knowm.xchange.currency.CurrencyPair
import org.slf4j.LoggerFactory

/**
  * Consumer for market data exchange products streams
  * @param last map to store elements depending on currency pair
  * @param pair the particular pair, which the new stream product is corresponded to
  * @tparam T
  */
class MarketDataConsumer[T](last: Map[CurrencyPair, LimitedQueue[T]], pair: CurrencyPair) extends Consumer[T] {

  val log = LoggerFactory.getLogger(classOf[MarketDataConsumer[T]])

  override def accept(t: T) =  last(pair).enqueue(t)

}

/**
  * Consumer to handle exceptions
  */
class MarketDataErrorConsumer extends Consumer[Throwable] {

  val log = LoggerFactory.getLogger(classOf[MarketDataConsumer[MarketDataErrorConsumer]])

  override def accept(t: Throwable) = log.error("Error acquired on subscription : {}", t.getMessage)

}
