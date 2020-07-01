package br.zup.dtp.pact.message.kafka;

import br.zup.dtp.pact.message.model.Client;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

   public static final String BOOTSTRAP_SERVERS = "localhost:9092";
   public static final String GROUP_ID = "clientGroup";

   @Bean
   public ConsumerFactory<String, Client> consumerFactory() {
      Map<String, Object> config = new HashMap<>();
      config.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
          BOOTSTRAP_SERVERS);
      config.put(
          ConsumerConfig.GROUP_ID_CONFIG,
          GROUP_ID);
      config.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class);
      config.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class);
      return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Client.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, Client> listenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, Client> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }

}
