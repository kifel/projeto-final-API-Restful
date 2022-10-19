package br.org.serratec.dto;

import java.time.LocalDate;

import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PedidoDTO {
    
    private LocalDate dataPedido;
    private LocalDate dataEnvio;
    private LocalDate dataEntrega;
    private String Status;
    private ClienteDTO cliente;

    public PedidoDTO(Pedido pedido) {
        this.dataPedido = pedido.getDataPedido();
        this.dataEnvio = pedido.getDataEnvio();
        this.dataEntrega = pedido.getDataEntrega();
        this.Status = pedido.getStatus();
        this.cliente = new ClienteDTO(pedido.getCliente());
    }
}