package org.dzavorin.xchange.utils

import org.knowm.xchange.currency.{Currency, CurrencyPair}

object Helper extends Helper

trait Helper {

  /** Transforms currency pair string into @entity CurrencyPair, currencies must be separated by underlying score "_"
    *
    * @param currency to be transformed (example "BTC_USD")
    * @return CurrencyPair
    */
  def getCurrencyPair(currency: String): CurrencyPair = {
    val split = currency.indexOf('_')
    if (split < 1) throw new IllegalArgumentException("Could not parse currency pair from '" + currency + "'")
    new CurrencyPair(
      Currency.getInstance(currency.substring(0, split)),
      Currency.getInstance(currency.substring(split + 1))
    )
  }

}
