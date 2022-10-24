package br.org.serratec.dto;

import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemPedidoListarDTO {


    private Long id;

    private Integer quantidade;

    private Double precoVenda;

    private Pedido pedido;

    private ProdutoDTO produto;

    public ItemPedidoListarDTO(ItemPedido itemPedido) {
        this.id = itemPedido.getId();
        this.quantidade = itemPedido.getQuantidade();
        this.precoVenda = itemPedido.getPrecoVenda();
        this.pedido = itemPedido.getPedido();
        this.produto = new ProdutoDTO(itemPedido.getProduto());
    }
}