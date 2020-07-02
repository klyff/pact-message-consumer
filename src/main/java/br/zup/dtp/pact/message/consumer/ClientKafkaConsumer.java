package br.zup.dtp.pact.message.consumer;

import static br.zup.dtp.pact.message.kafka.KafkaConsumerConfig.GROUP_ID;
import static br.zup.dtp.pact.message.kafka.KafkaTopicConfig.TOPIC_CLIENT;
import static br.zup.dtp.pact.message.kafka.KafkaTopicConfig.TOPIC_STRING;

import br.zup.dtp.pact.message.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@EnableKafka
@Component
public class ClientKafkaConsumer {

   @KafkaListener(topics = TOPIC_CLIENT, containerFactory = "listenerContainerFactory", groupId = GROUP_ID)
   public void clientListener(Client client) {
      log.info("Consuming message: "+client);
   }

   @KafkaListener(topics = { TOPIC_STRING },groupId = GROUP_ID)
   public void stringListener(String client) {
      log.info("Consuming message: "+client);
   }

}
