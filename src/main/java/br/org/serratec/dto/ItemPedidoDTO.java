package br.org.serratec.dto;

import java.io.Serializable;

import br.org.serratec.model.ItemPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemPedidoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Integer quantidade;
    
    private Double precoVenda;
    
    private ProdutoItemPedidoListDTO produto;

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this.id = itemPedido.getId();
        this.quantidade = itemPedido.getQuantidade();
        this.precoVenda = itemPedido.getPrecoVenda();
        this.produto = new ProdutoItemPedidoListDTO(itemPedido.getProduto());
    }
}