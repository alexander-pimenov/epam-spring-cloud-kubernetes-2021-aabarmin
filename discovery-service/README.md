Eureka Server
=

Eureka Server, либо в нашем случае Discovery-Service, должен стартовать первым, т.к. он является контейнером для остальных микросервисов.

Available modules
==

* `config-server` - Spring Cloud Config Server
* `discovery-service` - Service discovery with Eureka
* `examinator` - entry point, transparently sends requests to subsequent challenge providers
* `history` - provides challenges about history
* `mathematic` - provides challenges about math
* `api-gateway` - provides api-gateway
