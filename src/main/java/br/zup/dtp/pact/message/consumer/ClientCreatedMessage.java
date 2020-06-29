package br.zup.dtp.pact.message.consumer;

import br.zup.dtp.pact.message.model.Client;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClientCreatedMessage {

	@NotNull
	private String messageUuid;

	@NotNull
	private Client client;

}
