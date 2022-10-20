package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.model.Cliente;
import br.org.serratec.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<ClienteDTO> listarTodos() {
		List<Cliente> clientes = repository.findAll();
		
		return clientes.stream()
				.map(cliente -> new ModelMapper().map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<ClienteDTO> listarPorId(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		if (cliente.isEmpty()) {
			// Lançar uma exception
		}
		ClienteDTO dto = new ModelMapper().map(cliente.get(), ClienteDTO.class);
		return Optional.of(dto);
	}

	public ClienteDTO cadastrar(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Cliente cliente = mapper.map(clienteDTO, Cliente.class);
		cliente = repository.save(cliente);
		clienteDTO.setId(cliente.getIdCliente());
		return clienteDTO;
	}

	public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Cliente cliente = mapper.map(clienteDTO, Cliente.class);
		repository.save(cliente);
		return clienteDTO;
	}

	public void apagar(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		if (cliente.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
