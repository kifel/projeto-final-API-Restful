package br.org.serratec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_pedido")
    private Long id;

    private Integer quantidade;

    @Column(name = "preco_venda")
    private Double precoVenda;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

}