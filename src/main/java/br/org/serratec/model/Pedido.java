package br.org.serratec.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.org.serratec.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "data_envio")
    private LocalDate dataEnvio;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    

}
