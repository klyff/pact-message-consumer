package br.zup.dtp.pact.message.kafka;

import br.zup.dtp.pact.message.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class ClientKafkaConsumer {

   public static final String TOPIC = "client_creation";

   @KafkaListener(topics = TOPIC, containerFactory = "listenerConsumerFactory")
   public void clientListener(Client client) {

      log.info("Consuming message: "+client);


   }

}
