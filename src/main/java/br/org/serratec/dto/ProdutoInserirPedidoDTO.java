package br.org.serratec.dto;

import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProdutoInserirPedidoDTO {

    Long id;

    public ProdutoInserirPedidoDTO (Pedido pedido) {
        this.id = pedido.getId();
    }
    
}

