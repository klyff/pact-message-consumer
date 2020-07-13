package br.zup.dtp.pact.message.kafka;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.Matchers;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ClientCreationMessageProvider", providerType = ProviderType.ASYNCH)
public class MessageConsumerAsyncTest {

//   @MockBean
//   MessageClientKafkaConsumer kafkaConsumer;

   @BeforeEach
   void setUp() {
      System.setProperty("pact.verifier.publishResults", "true");
   }

   @Pact(provider = "ClientCreationMessageProvider", consumer = "ClientCreationMessageConsummer")
   public MessagePact createPactJsonMessage(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringValue("name", "KLYFF HARLLEY TOLEDO");
      body.stringValue("id", "1001");
      body.stringValue("type", "user");


      Map<String, String> metadata = new HashMap<String, String>();
      metadata.put("Content-Type", "application/json");

      return builder.given("ClientProviderState")
          .expectsToReceive("ClientProviderState CallBack Json")
          .withMetadata(metadata)
          .withContent(body)
          .toPact();
   }

   @Pact(provider = "ClientCreationMessageProvider", consumer = "ClientCreationMessageConsummer")
   public MessagePact createPactForMap(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringValue("name", "KLYFF HARLLEY TOLEDO");
      body.stringValue("id", "1001");
      body.stringValue("type", "user");


      Map<String, Object> metadata = new HashMap<>();
      metadata.put("destination", Matchers.regexp("\\w+\\d+", "X010"));

      return builder.given("ClientProviderState2")
          .expectsToReceive("Client Created Message")
          .withMetadata(metadata)
          .withContent(body)
          .toPact();
   }


   @Test
   @PactTestFor(pactMethod = "createPactJsonMessage")
   public void test2(MessagePact pact) {

      final String body = "{\"name\":\"KLYFF HARLLEY TOLEDO\",\"id\":\"1001\",\"type\":\"user\"}".trim();

//      Client clientMessage = kafkaConsumer.stringListener(pact.getMessages().get(0).contentsAsString());
//      Client clientLocal = kafkaConsumer.stringListener(body);
//      assert(clientMessage.equals(clientLocal));

      assertThat(new String(pact.getMessages().get(0).contentsAsBytes()), is(body));

   }

   @Test
   @PactTestFor(pactMethod = "createPactForMap")
   public void test(List<Message> messages) {
      final String body = "{\"name\":\"KLYFF HARLLEY TOLEDO\",\"id\":\"1001\",\"type\":\"user\"}".trim();

      assertThat(new String(messages.get(0).contentsAsBytes()), is(body));
      assertThat(messages.get(0).getMetaData(), hasEntry("destination", "X010"));
   }

}