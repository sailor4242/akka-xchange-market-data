[![Build Status](https://travis-ci.org/sailor4242/akka-xchange-adapter.svg?branch=master)](https://travis-ci.org/sailor4242/akka-xchange-adapter)

## Akka-XChange

**Crypto Currency Market Data REST Server**  
Based on [XChange-stream](https://github.com/bitrich-info/xchange-stream)  
App runs on port *8080*, and gives ypu functionality to retrieve market data 
by REST API:

    GET /marketdata/{platform_name}/{data_type}/{currency_pair}

***Platform_names:***
***bitstamp, bitmex, okcoin, binance, bitfinex***

***Data_types:***
***trade, order, tick***

***Currency_pairs:***
***BTC_USD, ETH_USD, LTC_USD***

You can easily expand supported functionality by adding additional Platforms 
or Currency pairs via source code. It's all ready for it.

**Tools:**  
Scala, Akka-HTTP, Akka-Stream, Akka-Remote Xchange-stream, Gson, Docker, Sbt

**TODO:**
* Add Web UI
* Web-socket connectivity
* Kafka or RabbitMQ integration
* Tests