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
import br.org.serratec.model.Categoria;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.repository.ClienteRepository;

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

	public ItemPedidoDTO cadastrar(ItemPedidoDTO itemPedidoDTO) {
		itemPedidoDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
		itemPedido = repository.save(itemPedido);
		itemPedidoDTO.setId(itemPedido.getId());
		return itemPedidoDTO;
	}

	public ItemPedidoDTO atualizar(Long id, ItemPedidoDTO itemPedidoDTO) {
		itemPedidoDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
		repository.save(itemPedido);
		return itemPedidoDTO;
	}

	public void apagar(Long id) {
		Optional<ItemPedido> itemPedido = repository.findById(id);
		if (itemPedido.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
