FROM consul
COPY ./consul-import.json /usr/src/consul/
WORKDIR /usr/src/consul
ENV CONSUL_HTTP_ADDR=consul:8500
ENTRYPOINT until consul kv import @consul-import.json; do echo "Waiting..."; sleep 2; done