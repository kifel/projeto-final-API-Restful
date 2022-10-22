package br.org.serratec.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.org.serratec.model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProdutoInserirDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    @NotBlank(message = "Digite um nome valido para o produto")
    @Size(max = 30)
    private String nome;

    @NotBlank(message = "Digite um descrição valido para o produto")
    @Size(max = 100)
    private String descricao;

    @NotNull(message = "Digite uma quantidade valida")
    private Integer qtdEstoque;

    private LocalDate dataCadastro;

    @NotNull(message = "Digite um valor valido para o produto")
    private Double valorUnitario;
    
    private ProdutoInserirCategoriaDTO categoria;

    public ProdutoInserirDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.qtdEstoque = produto.getQtdEstoque();
        this.dataCadastro = produto.getDataCadastro();
        this.valorUnitario = produto.getValorUnitario();
        this.categoria = new ProdutoInserirCategoriaDTO(produto.getCategoria());
    }
}
