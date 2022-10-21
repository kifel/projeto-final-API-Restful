package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	public List<EnderecoDTO> listarTodos() {
		List<Endereco> enderecos = repository.findAll();

		return enderecos.stream().map(endereco -> new ModelMapper().map(endereco, EnderecoDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<EnderecoDTO> listarPorId(Long id) {
		Optional<Endereco> endereco = repository.findById(id);
		if (endereco.isEmpty()) {
			// Lançar uma exception
		}
		EnderecoDTO dto = new ModelMapper().map(endereco.get(), EnderecoDTO.class);
		return Optional.of(dto);
	}

	public EnderecoDTO cadastrar(EnderecoInserirDTO enderecoInserirDTO) {
		enderecoInserirDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Endereco endereco = mapper.map(enderecoInserirDTO, Endereco.class);
		endereco = repository.save(endereco);
		enderecoInserirDTO.setId(endereco.getId());
		return new EnderecoDTO(endereco);
	}

	public EnderecoDTO atualizar(Long id, EnderecoInserirDTO enderecoInserirDTO) {
		enderecoInserirDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Endereco endereco = mapper.map(enderecoInserirDTO, Endereco.class);
		repository.save(endereco);
		return new EnderecoDTO(endereco);
	}

	public Boolean apagar(Long id) {
		Optional<Endereco> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

}
