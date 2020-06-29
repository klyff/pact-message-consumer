package br.zup.dtp.pact.message.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

   private ObjectMapper objectMapper;

   public MessageConsumer(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }

   @KafkaListener(topics = MessageConsumerConfigurationKafka.TOPIC)
   public void consumeStringMessage(String messageString) throws IOException {
      log.info("Consuming message '{}'", messageString);

      ClientCreatedMessage message = objectMapper.readValue(messageString, ClientCreatedMessage.class);

      Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

      Set<ConstraintViolation<ClientCreatedMessage>> violations = validator.validate(message);

      if (!violations.isEmpty()) {
         throw new ConstraintViolationException(violations);
      }
      log.info("Message was Consumed `{}`", messageString);
      // Message to Business Rules...
   }

}
