package br.org.serratec.dto;

import br.org.serratec.model.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteListarPedidoDTO {
    
    private Long id;
    private String nomeUsuario;
    private String nomeCompleto;

    public ClienteListarPedidoDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.nomeUsuario = cliente.getNomeUsuario();
    }
}
