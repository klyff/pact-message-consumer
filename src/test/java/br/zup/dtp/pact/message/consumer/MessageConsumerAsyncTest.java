package br.zup.dtp.pact.message.consumer;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "MessageProvider", providerType = ProviderType.ASYNCH)
public class MessageConsumerAsyncTest {

   @Pact(consumer = "MessageConsumer-Spec3")
   public MessagePact createPactMap(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringValue("name", "KLYFF HARLLEY TOLEDO");
      body.stringValue("id", "1001");
      body.stringValue("type", "user");


      Map<String, Object> metadata = new HashMap<>();
      metadata.put("destination", Matchers.regexp("\\w+\\d+", "X010"));

      return builder.given("ProviderState")
          .expectsToReceive("First Test Map Message")
          .withMetadata(metadata)
          .withContent(body)
          .toPact();
   }

   @Pact(provider = "MessageProvider", consumer = "MessageConsumer-Spec3")
   public MessagePact createPactJsonMessage(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringValue("name", "KLYFF HARLLEY TOLEDO");
      body.stringValue("id", "1001");
      body.stringValue("type", "user");


      Map<String, String> metadata = new HashMap<String, String>();
      metadata.put("Content-Type", "application/json");

      return builder.given("ProviderState2")
          .expectsToReceive("Second test JSON Message")
          .withMetadata(metadata)
          .withContent(body)
          .toPact();
   }

   @Test
   @PactTestFor(pactMethod = "createPactMap")
   public void test(List<Message> messages) {
      final String body = "{\"name\":\"KLYFF HARLLEY TOLEDO\",\"id\":\"1001\",\"type\":\"user\"}".trim();

      assertThat(new String(messages.get(0).contentsAsBytes()), is(body));
      assertThat(messages.get(0).getMetaData(), hasEntry("destination", "X010"));
   }

   @Test
   @PactTestFor(pactMethod = "createPactJsonMessage")
   public void test2(MessagePact pact) {
      final String body = "{\"name\":\"KLYFF HARLLEY TOLEDO\",\"id\":\"1001\",\"type\":\"user\"}".trim();

      assertThat(new String(pact.getMessages().get(0).contentsAsBytes()), is(body));
   }
}