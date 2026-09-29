#!/bin/sh

echo "Aguardando Graylog..."

until curl -s -f \
  -u admin:admin \
  http://graylog:9000/api/system/lbstatus \
  > /dev/null
do
  sleep 5
done

echo "Graylog disponível. Verificando GELF Input..."

INPUT_EXISTS=$(curl -s \
  -u admin:admin \
  http://graylog:9000/api/system/inputs \
  | grep -c "GELF UDP" || true)

if [ "$INPUT_EXISTS" -eq 0 ]; then

    echo "GELF Input não encontrado. Criando..."

    curl -u admin:admin \
      -H "X-Requested-By: graylog-init" \
      -H "Content-Type: application/json" \
      -X POST \
      http://graylog:9000/api/system/inputs \
      -d '{
        "title":"GELF UDP",
        "type":"org.graylog2.inputs.gelf.udp.GELFUDPInput",
        "global":true,
        "configuration":{
          "bind_address":"0.0.0.0",
          "port":12201,
          "recv_buffer_size":262144,
          "number_worker_threads":2,
          "decompress_size_limit":8388608
        }
      }'

    echo "GELF Input criado."

else

    echo "GELF Input já existe. Nenhuma alteração necessária."

fi

echo "graylog-init finalizado."