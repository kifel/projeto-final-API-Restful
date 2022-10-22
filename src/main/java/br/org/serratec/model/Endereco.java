package br.org.serratec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private Long id;

	private String cep;

	@Column(name = "rua")
	private String logradouro;

	private String complemento;

	private String bairro;

	@Column(name = "cidade")
	private String localidade;

	@Column(name = "estado")
	private String uf;

	private Integer numero;

}