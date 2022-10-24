package br.org.serratec.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    private String nome;

    private String descricao;

    @Column(name = "qtd_estoque")
    private Integer qtdEstoque;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Lob     
    private byte[] imagem;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "produto")
    private Set<ItemPedido> items = new HashSet<>();
}
