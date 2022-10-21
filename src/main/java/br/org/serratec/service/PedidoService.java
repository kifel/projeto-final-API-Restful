package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.model.Pedido;
import br.org.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public List<PedidoDTO> listarTodos() {
		List<Pedido> pedidos = repository.findAll();
		
		return pedidos.stream()
				.map(pedido -> new ModelMapper().map(pedido, PedidoDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<PedidoDTO> listarPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (pedido.isEmpty()) {
			// Lançar uma exception
		}
		PedidoDTO dto = new ModelMapper().map(pedido.get(), PedidoDTO.class);
		return Optional.of(dto);
	}

	public PedidoDTO cadastrar(PedidoInserirDTO pedidoInserirDTO) {
		pedidoInserirDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Pedido pedido = mapper.map(pedidoInserirDTO, Pedido.class);
		pedido = repository.save(pedido);
		pedidoInserirDTO.setId(pedido.getId());
		return new PedidoDTO(pedido);
	}

	public PedidoDTO atualizar(Long id, PedidoInserirDTO pedidoInserirDTO) {
		pedidoInserirDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Pedido pedido = mapper.map(pedidoInserirDTO, Pedido.class);
		repository.save(pedido);
		return new PedidoDTO(pedido);
	}

	public Boolean apagar(Long id) {
		Optional<Pedido> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false;
	}

}
