package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.model.Categoria;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.repository.ClienteRepository;

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

	public PedidoDTO cadastrar(PedidoDTO pedidoDTO) {
		pedidoDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Pedido pedido = mapper.map(pedidoDTO, Pedido.class);
		pedido = repository.save(pedido);
		pedidoDTO.setId(pedido.getId());
		return pedidoDTO;
	}

	public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTO) {
		pedidoDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Pedido pedido = mapper.map(pedidoDTO, Pedido.class);
		repository.save(pedido);
		return pedidoDTO;
	}

	public void apagar(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		if (pedido.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
