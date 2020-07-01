package br.zup.dtp.pact.message.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Client {

	private long id;

	private String name;

	private String type;

}
