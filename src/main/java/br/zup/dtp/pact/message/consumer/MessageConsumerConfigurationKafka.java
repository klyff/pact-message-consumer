package br.zup.dtp.pact.message.consumer;

import br.zup.dtp.pact.message.model.Client;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class MessageConsumerConfigurationKafka {

   public static final String TOPIC = "ClientTopic";

   @Value(value = "${kafka.bootstrapAddress}")
   private String bootstrapAddress;

   @Value(value = "${kafka.groupId}")
   private String groupId;

   Map<String, Object> configProps = new HashMap<>();

   @Bean
   public ProducerFactory<String, String> producerFactory() {
      configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
          bootstrapAddress);
      configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class);
      configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class);
      return new DefaultKafkaProducerFactory<>(configProps);
   }


   @Bean
   public ConsumerFactory<String, Client> clientConsumerFactory() {
      return new DefaultKafkaConsumerFactory<>(
          configProps,
          new StringDeserializer(),
          new JsonDeserializer<>(Client.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, Client>
   clientKafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, Client> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(clientConsumerFactory());
      return factory;
   }

}
