package br.org.serratec.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Long idProduto;

    @Size(max = 30)
    @NotNull(message = "Insira o nome do produto.")
    private String nomeProduto;

    @Size(max = 100)
    private String descricaoProduto;

    @NotNull(message = "Insira a quantidade em estoque.")
    private Integer qtdEstoque;

    private Date dataCadastro;

    @NotNull(message = "Insira o valor do produto.")
    private Float valorUnitario;

}
