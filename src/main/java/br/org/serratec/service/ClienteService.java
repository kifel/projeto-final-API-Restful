package br.org.serratec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.model.Cliente;
import br.org.serratec.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> listarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente cadastrar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente atualizar(Long id, Cliente cliente) {
		cliente.setIdCliente(id); // resolvido
		return clienteRepository.save(cliente);
	}

	public void apagar(Long id) {
		clienteRepository.deleteById(id);
	}

}
