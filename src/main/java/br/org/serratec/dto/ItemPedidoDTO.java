package br.org.serratec.dto;

import br.org.serratec.model.ItemPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemPedidoDTO {

    private Integer quantidade;
    private Integer precoVenda;
    private PedidoDTO pedido;
    private ProdutoDTO produto;

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this.quantidade = itemPedido.getQuantidade();
        this.precoVenda = itemPedido.getPrecoVenda();
        this.pedido = new PedidoDTO(itemPedido.getPedido());
        this.produto = new ProdutoDTO(itemPedido.getProduto());
    }
}
