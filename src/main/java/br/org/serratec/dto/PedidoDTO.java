package br.org.serratec.dto;

import java.time.LocalDate;

import br.org.serratec.enums.Status;
import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PedidoDTO {
    
    private Long id;
    private LocalDate dataPedido;
    private LocalDate dataEnvio;
    private LocalDate dataEntrega;
    private Status status;
    private ClienteListarPedidoDTO cliente;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        this.dataEnvio = pedido.getDataEnvio();
        this.dataEntrega = pedido.getDataEntrega();
        this.status = pedido.getStatus();
        this.cliente = new ClienteListarPedidoDTO(pedido.getCliente());
    }
}