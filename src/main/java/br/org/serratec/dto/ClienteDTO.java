package br.org.serratec.dto;

import java.time.LocalDate;
import java.util.List;

import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteDTO {

	private Long id;
    private String email;
    private String nomeUsuario;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
    private Endereco endereco;
    private List<Pedido> pedido;

    public ClienteDTO(Cliente cliente) {
    	this.id = cliente.getIdCliente();
        this.email = cliente.getEmail();
        this.nomeUsuario = cliente.getNomeUsuario();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.dataNascimento = cliente.getDataNascimento();
        this.endereco = cliente.getEndereco();
        this.pedido = cliente.getPedido();
    }
}

