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
import br.org.serratec.model.Categoria;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.ClienteRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	public List<EnderecoDTO> listarTodos() {
		List<Endereco> enderecos = repository.findAll();
		
		return enderecos.stream()
				.map(endereco -> new ModelMapper().map(endereco, EnderecoDTO.class))
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

	public EnderecoDTO cadastrar(EnderecoDTO enderecoDTO) {
		enderecoDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
		endereco = repository.save(endereco);
		enderecoDTO.setId(endereco.getId());
		return enderecoDTO;
	}

	public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) {
		enderecoDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
		repository.save(endereco);
		return enderecoDTO;
	}

	public void apagar(Long id) {
		Optional<Endereco> endereco = repository.findById(id);
		if (endereco.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
