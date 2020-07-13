
Entrar no Docker Bash
docker exec -it kafka_kafka_1 bash

Iniciar um Topico
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic client_creation


kafka-console-producer.sh --broker-list localhost:9092 --topic client_creation
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic client_creation