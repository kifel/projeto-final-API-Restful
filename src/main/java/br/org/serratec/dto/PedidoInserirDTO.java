package br.org.serratec.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.org.serratec.enums.Status;
import br.org.serratec.model.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PedidoInserirDTO {
    
    private Long id;
    
    @NotNull(message = "Digite uma data valida")
    private LocalDate dataPedido;

    @NotNull(message = "Digite uma data valida")
    private LocalDate dataEnvio;
    
    @NotNull(message = "Digite uma data valida")
    private LocalDate dataEntrega;

    @NotNull(message = "Digite um status valido")
    private Status status;
    
    private ClienteInserirPedidoDTO cliente;

    public PedidoInserirDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        this.dataEnvio = pedido.getDataEnvio();
        this.dataEntrega = pedido.getDataEntrega();
        this.status = pedido.getStatus();
        this.cliente = new ClienteInserirPedidoDTO(pedido.getCliente());
    }
}
