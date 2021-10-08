echo off
echo Starting up new instance named '%1'
echo Make sure you created the analytics db with the name '%1%'
echo Make sure you created the following Consul KV files: '%1', '%1,prod', '%1-analytics', '%1-analytics,prod'
pause

docker run -d -t -i ^
-e "PGDATA=/var/lib/postgresql/data" ^
-e "POSTGRES_USER=admin" ^
-e "POSTGRES_PASSWORD=password" ^
-e "POSTGRES_DB=couponmanager" ^
--network coupunch_coupunch-prod-network ^
--name %1_db ^
postgres:13.4

timeout /t 10 /nobreak

docker run -d -t -i ^
-e "COUPONMANAGER_INSTANCE=%1" ^
-e "SPRING_PROFILES_ACTIVE=prod" ^
-e "SPRING_CLOUD_CONSUL_HOST=consul_prod" ^
--network coupunch_coupunch-prod-network ^
--name %1-instance ^
couponmanager:0.0.1-SNAPSHOT

docker run -d -t -i ^
-e "COUPONMANAGER_INSTANCE=%1" ^
-e "SPRING_PROFILES_ACTIVE=prod" ^
-e "SPRING_CLOUD_CONSUL_HOST=consul_prod" ^
--network coupunch_coupunch-prod-network ^
--name %1-analytics-instance ^
analytics:0.0.1-SNAPSHOT