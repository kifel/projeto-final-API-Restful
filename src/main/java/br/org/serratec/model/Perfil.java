package br.org.serratec.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_perfil")
	private Long idPerfil;
	private String nome;

	@OneToMany(mappedBy = "id.perfil")
	private Set<ClientePerfil> clientePerfil = new HashSet<>();

	public Set<ClientePerfil> getClientePerfil() {
		return clientePerfil;
	}

	public void setClientePerfil(Set<ClientePerfil> clientePerfil) {
		this.clientePerfil = clientePerfil;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
