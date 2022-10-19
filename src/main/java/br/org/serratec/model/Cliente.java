package br.org.serratec.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long idCliente;

	@NotNull(message = "Insira um usuario valido.")
	@Column(name = "nome_usuario")
	@Size(max = 20)
	private String nomeUsuario;

	@NotNull(message = "Insira seu nome completo.")
	@Column(name = "nome_completo")
	@Size(max = 60)
	private String nomeCompleto;

	@NotNull(message = "Insira um E-mail valido.")
	@Size(max = 30)
	private String email;

	@NotNull(message = "Insira uma senha(Min 8 character).")
	@Size(min = 8)
	@Size(max = 255)
	private String senha;

	@NotNull
	@CPF(message = "Insira um CPF valido.")
	@Size(max = 14)
	private String cpf;

	@NotNull(message = "Insira um numero de telefone.")
	@Size(min = 11)
	@Size(max = 11)
	private String telefone;

	@NotNull(message = "Insira a data de nascimento.")
	private LocalDate dataNascimento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@JsonManagedReference
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedido;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		return "Id:" + idCliente + "\n" + "Nome Completo:" + nomeCompleto + "Email:" + email + "\n" + "CPF:" + cpf
				+ "\n" + "Telefone:" + telefone + "\n" + "Data de Nascimento:" + dataNascimento;
	}

}