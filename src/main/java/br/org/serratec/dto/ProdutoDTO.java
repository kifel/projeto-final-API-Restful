package br.org.serratec.dto;

import java.time.LocalDate;

import br.org.serratec.model.Categoria;
import br.org.serratec.model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Integer qtdEstoque;
    private LocalDate dataCadastro;
    private Double valorUnitario;
    private Categoria categoria;

    private String uri;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.qtdEstoque = produto.getQtdEstoque();
        this.dataCadastro = produto.getDataCadastro();
        this.valorUnitario = produto.getValorUnitario();
        this.categoria = produto.getCategoria();
    }
}
