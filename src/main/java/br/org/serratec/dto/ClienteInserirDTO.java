package br.org.serratec.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.org.serratec.model.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteInserirDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "você deve digitar um email valido")
    @Email(message = "digite um email valido")
    private String email;

    @NotBlank(message = "você deve digitar um nome de usuário valido")
    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @NotBlank(message = "você deve digitar um nome")
    @Column(name = "nome_completo")
    private String nomeCompleto;

    @NotBlank(message = "você deve digitar uma senha")
    @Size(min = 8)
    private String senha;

    @NotBlank(message = "você deve digitar um cpf valido")
    @CPF(message = "você deve digitar um cpf valido")
    private String cpf;

    @NotBlank(message = "você deve digitar um telefone valido")
    @Size
    private String telefone;

    @NotNull(message = "você deve digitar uma data correta")
    @Past
    @Column(name = "data_nasc")
    private LocalDate dataNascimento;

    @NotNull(message = "você deve completar os dados corretamente")
    private EnderecoInserirDTO endereco;

    public ClienteInserirDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getEmail();
        this.nomeUsuario = cliente.getNomeUsuario();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.dataNascimento = cliente.getDataNascimento();
        this.senha = cliente.getSenha();
        this.endereco = new EnderecoInserirDTO(cliente.getEndereco());
    }
}
