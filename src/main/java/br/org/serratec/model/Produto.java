package br.org.serratec.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @NotBlank(message = "Digite um nome valido para o produto")
    @Size(max = 30)
    @Column(name = "nome_produto")
    private String nomeProduto;

    @NotBlank(message = "Digite um descrição valido para o produto")
    @Size(max = 100)
    private String descricao;

    @Column(name = "qtd_estoque")
    private Integer qtdEstoque;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @NotNull(message = "Digite um valor valido para o produto")
    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Lob     
    private byte[] imagem;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}