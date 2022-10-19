package br.org.serratec.dto;

import br.org.serratec.model.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getIdCliente();
        this.nome = cliente.getNomeCompleto();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
    }

}
