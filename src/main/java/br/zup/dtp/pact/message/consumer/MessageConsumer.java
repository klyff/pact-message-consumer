package br.zup.dtp.pact.message.consumer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

   private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

   private ObjectMapper objectMapper;

   public MessageConsumer() {
      this.objectMapper = new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   }

   public MessageConsumer(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }


   public void consumeStringMessage(String messageString) throws IOException {
      logger.info("Consuming message '{}'", messageString);

      ClientCreatedMessage message = objectMapper.readValue(messageString, ClientCreatedMessage.class);

      Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

      Set<ConstraintViolation<ClientCreatedMessage>> violations = validator.validate(message);

//      if (!violations.isEmpty()) {
//         throw new ConstraintViolationException(violations);
//      }
      logger.info("Message was Consumed `{}`", messageString);
      // Message to Business Rules...
   }

}
