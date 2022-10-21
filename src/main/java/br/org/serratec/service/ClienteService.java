package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.serratec.config.MailConfig;
import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.exception.EmailException;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.ClienteRepository;
import br.org.serratec.repository.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MailConfig mailConfig;

	public List<ClienteDTO> listarTodos() {
		List<Cliente> clientes = repository.findAll();

		return clientes.stream().map(cliente -> new ModelMapper().map(cliente, ClienteDTO.class))
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

	public ClienteDTO cadastrar(ClienteInserirDTO clienteInserirDTO) {
		if (repository.findByEmail(clienteInserirDTO.getEmail()) != null) {
			throw new EmailException("Email existente na base");
		}
		Cliente novoCliente = new Cliente();
		novoCliente.setEmail(clienteInserirDTO.getEmail());
		novoCliente.setNomeUsuario(clienteInserirDTO.getNomeUsuario());
		novoCliente.setNomeCompleto(clienteInserirDTO.getNomeCompleto());
		novoCliente.setCpf(clienteInserirDTO.getCpf());
		novoCliente.setTelefone(clienteInserirDTO.getTelefone());
		novoCliente.setDataNascimento(clienteInserirDTO.getDataNascimento());
		novoCliente.setEndereco(clienteInserirDTO.getEndereco());
		novoCliente.setSenha(bCryptPasswordEncoder.encode(clienteInserirDTO.getSenha()));
		novoCliente = repository.save(novoCliente);

		return new ClienteDTO(novoCliente);
	}

	public ClienteDTO atualizar(Long id, ClienteInserirDTO clienteInserirDTO) {
		clienteInserirDTO.setId(id);
		Cliente novoCliente = new Cliente();
		novoCliente.setId(clienteInserirDTO.getId());
		novoCliente.setEmail(clienteInserirDTO.getEmail());
		novoCliente.setNomeUsuario(clienteInserirDTO.getNomeUsuario());
		novoCliente.setNomeCompleto(clienteInserirDTO.getNomeCompleto());
		novoCliente.setCpf(clienteInserirDTO.getCpf());
		novoCliente.setTelefone(clienteInserirDTO.getTelefone());
		novoCliente.setDataNascimento(clienteInserirDTO.getDataNascimento());
		novoCliente.setEndereco(clienteInserirDTO.getEndereco());
		novoCliente.setSenha(bCryptPasswordEncoder.encode(clienteInserirDTO.getSenha()));
		novoCliente = repository.save(novoCliente);

		mailConfig.sendEmail(clienteInserirDTO.getEmail(), "Cadastro de Usuário", novoCliente.toString());

		return new ClienteDTO(novoCliente);
	}

	public Boolean apagar(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		Optional<Endereco> endereco = enderecoRepository.findById(id);
		if (cliente.isPresent() && endereco.isPresent()) {
			repository.deleteById(id);
			enderecoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
