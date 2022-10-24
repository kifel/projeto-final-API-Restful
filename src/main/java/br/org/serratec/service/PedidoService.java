package br.org.serratec.service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.dto.PedidoInserirDTO;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Pedido;
import br.org.serratec.repository.ClienteRepository;
import br.org.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
    private ClienteRepository clienteRepository;

	public List<PedidoDTO> listarTodos() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return pedidos.stream().map(pedido -> new ModelMapper().map(pedido, PedidoDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<PedidoDTO> listarPorId(Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			return null;
		}
		PedidoDTO dto = new ModelMapper().map(pedido.get(), PedidoDTO.class);
		return Optional.of(dto);
	}

	public PedidoDTO cadastrar(PedidoInserirDTO pedido) {
		Optional<Cliente> cliente = clienteRepository.findById(pedido.getCliente().getId());
		
		if (!cliente.isPresent()) {
            throw new FindException("Id de cliente não encontrado");
        }

		Pedido novoPedido = new Pedido();
		novoPedido.setDataPedido(pedido.getDataPedido());
		novoPedido.setDataEnvio(pedido.getDataEnvio());
		novoPedido.setDataEntrega(pedido.getDataEntrega());
		novoPedido.setStatus(pedido.getStatus());
		novoPedido.setCliente(cliente.get());
		novoPedido = pedidoRepository.save(novoPedido);

		return new PedidoDTO(novoPedido);
	}

	public PedidoDTO atualizar(Long id, PedidoInserirDTO pedido) {
		Optional<Cliente> cliente = clienteRepository.findById(pedido.getCliente().getId());
		
        if (!cliente.isPresent()) {
            throw new FindException("Id de cliente não encontrado");
        }
		
        pedido.setId(id);
        
		Pedido novoPedido = new Pedido();
		novoPedido.setId(pedido.getId());
		novoPedido.setDataPedido(pedido.getDataPedido());
		novoPedido.setDataEnvio(pedido.getDataEnvio());
		novoPedido.setDataEntrega(pedido.getDataEntrega());
		novoPedido.setStatus(pedido.getStatus());
		novoPedido.setCliente(cliente.get());
		novoPedido = pedidoRepository.save(novoPedido);

		return new PedidoDTO(novoPedido);
	}

	public Boolean apagar(Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
		if (pedido.isPresent()) {
			pedidoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
