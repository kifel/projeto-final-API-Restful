package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository repository;

	public List<ItemPedidoDTO> listarTodos() {
		List<ItemPedido> itemPedidos = repository.findAll();
		
		return itemPedidos.stream()
				.map(itemPedido -> new ModelMapper().map(itemPedido, ItemPedidoDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<ItemPedidoDTO> listarPorId(Long id) {
		Optional<ItemPedido> itemPedido = repository.findById(id);
		if (itemPedido.isEmpty()) {
			// Lançar uma exception
		}
		ItemPedidoDTO dto = new ModelMapper().map(itemPedido.get(), ItemPedidoDTO.class);
		return Optional.of(dto);
	}

	public ItemPedidoDTO cadastrar(ItemPedidoInserirDTO itemPedidoInserirDTO) {
		itemPedidoInserirDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		ItemPedido itemPedido = mapper.map(itemPedidoInserirDTO, ItemPedido.class);
		itemPedido = repository.save(itemPedido);
		itemPedidoInserirDTO.setId(itemPedido.getId());
		return new ItemPedidoDTO(itemPedido);
	}

	public ItemPedidoDTO atualizar(Long id, ItemPedidoInserirDTO itemPedidoInserirDTO) {
		itemPedidoInserirDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		ItemPedido itemPedido = mapper.map(itemPedidoInserirDTO, ItemPedido.class);
		repository.save(itemPedido);
		return new ItemPedidoDTO(itemPedido);
	}

	public Boolean apagar(Long id) {
		Optional<ItemPedido> itemPedido = repository.findById(id);
		if (itemPedido.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false;
	}

}
