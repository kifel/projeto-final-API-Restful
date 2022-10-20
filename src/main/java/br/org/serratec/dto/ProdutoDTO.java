package br.org.serratec.dto;

import java.time.LocalDate;

import br.org.serratec.model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProdutoDTO {
    
	private Long id;
    private String nomeProduto;
    private String descricao;
    private Integer qtdEstoque;
    private LocalDate dataCadastro;
    private Double valorUnitario;
    private byte[] imagem;
    private CategoriaDTO categoria;

    public ProdutoDTO(Produto produto) {
    	this.id = produto.getId();
        this.nomeProduto = produto.getNomeProduto();
        this.descricao = produto.getDescricao();
        this.qtdEstoque = produto.getQtdEstoque();
        this.dataCadastro = produto.getDataCadastro();
        this.valorUnitario = produto.getValorUnitario();
        this.imagem = produto.getImagem();
        this.categoria = new CategoriaDTO(produto.getCategoria());
    }
}
