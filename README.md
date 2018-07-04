[![Build Status](https://travis-ci.org/sailor4242/akka-xchange-market-data.svg?branch=master)](https://travis-ci.org/sailor4242/akka-xchange-market-data)

## Akka-XChange

**Crypto Currency Market Data REST Service**  
Based on [XChange-stream](https://github.com/bitrich-info/xchange-stream)  
App gives you functionality to retrieve crypto currency market data by REST API:

    GET /marketdata/{platform_name}/{data_type}/{currency_pair}

**Platform_names:**
bitstamp, bitmex, okcoin, binance, bitfinex

**Data_types:**
trade, order, tick

**Currency_pairs:**
BTC_USD, ETH_USD, LTC_USD

You can easily expand supported functionality by adding platforms 
and currency pairs via source code. It's all ready for it.

**Tools:**  
Scala, Akka-HTTP, Akka-Stream, Akka-Remote Xchange-stream, Gson, Docker, Sbt

**TODO:**
* Add Web UI
* Web-socket connectivity
* Kafka or RabbitMQ integration
* Tests