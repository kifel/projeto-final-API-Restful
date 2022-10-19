package br.org.serratec.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long idCliente;
	private String nome;
	private String email;
	private String senha;
	private String cpf;

	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER)
	private Set<Perfil> clientePerfil = new HashSet<>(); 
	
	@ManyToOne
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;
	
	@Override
	public String toString() {
		return "Id:" + idCliente +"\n"+ "Nome:" + nome +"\n"+ "Email" + email +"\n"+ "CPF:" + cpf; 
	}

    public Set<Perfil> getClientePerfil() {
		return clientePerfil;
	}

	public void setClientePerfil(Set<Perfil> clientePerfil) {
		this.clientePerfil = clientePerfil;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}