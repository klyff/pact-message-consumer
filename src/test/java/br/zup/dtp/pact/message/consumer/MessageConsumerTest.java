package br.zup.dtp.pact.message.consumer;


import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.MessagePact;
import au.com.dius.pact.provider.PactVerifyProvider;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ClientProvider", port = "1233")
public class MessageConsumerTest {


//   public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);
   private byte[] currentMessage;

   @Autowired
   private MessageConsumer messageConsumer;

   @Pact(provider = "ClientProvider", consumer = "ClientConsummer")
   public MessagePact userCreatedMessagePact(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringType("messageUuid");
      body.object("client")
          .numberType("id", 101L)
          .stringType("name", "Klyff Harlley Toledo")
          .closeObject();

      return builder
          .expectsToReceive("A Client was created message")
          .withContent(body)
          .toPact();
   }

   @Test
//   @PactTestFor(providerType = ProviderType.ASYNCH, pactMethod = "userCreatedMessagePact", providerName = "ClientConsummer")
   @PactVerifyProvider("userCreatedMessagePact")
   public void verifyCreatePersonPact() throws IOException {
      messageConsumer.consumeStringMessage(new String(this.currentMessage));
   }

   /**
    * This method is called by the Pact framework.
    */
   public void setMessage(byte[] message) {
      this.currentMessage = message;
   }

}
