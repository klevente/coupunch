#!/bin/sh

docker run -d -t -i \
-e PGDATA='/var/lib/postgresql/data' \
-e POSTGRES_USER='admin' \
-e POSTGRES_PASSWORD='password' \
-e POSTGRES_DB='couponmanager' \
--network 'coupunch-prod-network' \
--name 'couponmanager_db'