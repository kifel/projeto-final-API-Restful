package br.org.serratec.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cliente_perfil")
public class ClientePerfil {

	@EmbeddedId
	private ClientePerfilPK id = new ClientePerfilPK();
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	public ClientePerfil() {
	}

	public ClientePerfil(Cliente cliente, Perfil perfil, LocalDate dataCriacao) {
		id.setCliente(cliente);
		id.setPerfil(perfil);
		this.dataCriacao = dataCriacao;
	}

	public void setCliente(Cliente cliente) {
		id.setCliente(cliente);
	}

	public void setPerfil(Perfil perfil) {
		id.setPerfil(perfil);
	}

	public Cliente getCliente() {
		return id.getCliente();
	}

	public Perfil getPerfil() {
		return id.getPerfil();
	}

	public ClientePerfilPK getId() {
		return id;
	}

	public void setId(ClientePerfilPK id) {
		this.id = id;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}