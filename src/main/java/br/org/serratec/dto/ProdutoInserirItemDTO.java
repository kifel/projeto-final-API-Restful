package br.org.serratec.dto;

import br.org.serratec.model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProdutoInserirItemDTO {
    
    private Long idProduto;

    public ProdutoInserirItemDTO(Produto produto) {
        this.idProduto = produto.getId();
    } 
}