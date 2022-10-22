package br.org.serratec.dto;

import br.org.serratec.model.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteInserirPedidoDTO {
    
    private Long id;

    public ClienteInserirPedidoDTO (Cliente cliente) {
        this.id = cliente.getId();
    }
}
