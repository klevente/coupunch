FROM consul
COPY ./consul-import-prod.json /usr/src/consul/
WORKDIR /usr/src/consul
ENV CONSUL_HTTP_ADDR=consul_prod:8500
ENTRYPOINT until consul kv import @consul-import-prod.json; do echo "Waiting..."; sleep 2; done