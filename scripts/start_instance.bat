docker run -d -t -i ^
-e "COUPONMANAGER_INSTANCE=%1" ^
-e "SPRING_PROFILES_ACTIVE=prod" ^
-e "SPRING_CLOUD_CONSUL_HOST=consul_prod" ^
--network coupunch_coupunch-prod-network ^
--name %1-instance ^
couponmanager:0.0.1-SNAPSHOT