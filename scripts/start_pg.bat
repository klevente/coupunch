docker run -d -t -i ^
-e "PGDATA=/var/lib/postgresql/data" ^
-e "POSTGRES_USER=admin" ^
-e "POSTGRES_PASSWORD=password" ^
-e "POSTGRES_DB=couponmanager" ^
--network coupunch_coupunch-prod-network ^
--name couponmanager_db ^
postgres:13.4