Eureka Server
=

API Gateway, лучше стартовать последним, чтобы считались все настройки из других запущенных микросервисов.

Available modules
==

* `config-server` - Spring Cloud Config Server
* `discovery-service` - Service discovery with Eureka
* `examinator` - entry point, transparently sends requests to subsequent challenge providers
* `history` - provides challenges about history
* `mathematic` - provides challenges about math
* `api-gateway` - provides api-gateway
