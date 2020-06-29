package br.zup.dtp.pact.message.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Client {

	@NotNull
	private long id;

	@NotNull
	private String name;

}
