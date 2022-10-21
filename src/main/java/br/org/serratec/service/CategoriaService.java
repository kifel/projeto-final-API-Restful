package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.model.Categoria;
import br.org.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<CategoriaDTO> listarTodos() {
		List<Categoria> categorias = repository.findAll();
		
		return categorias.stream()
				.map(categoria -> new ModelMapper().map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<CategoriaDTO> listarPorId(Long id) {
		Optional<Categoria> categoria = repository.findById(id);
		if (categoria.isEmpty()) {
			// Lançar uma exception
		}
		CategoriaDTO dto = new ModelMapper().map(categoria.get(), CategoriaDTO.class);
		return Optional.of(dto);
	}

	public CategoriaDTO cadastrar(CategoriaInserirDTO categoriaInserirDTO) {
		categoriaInserirDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Categoria categoria = mapper.map(categoriaInserirDTO, Categoria.class);
		categoria = repository.save(categoria);
		categoriaInserirDTO.setId(categoria.getIdCategoria());
		return new CategoriaDTO(categoria);
	}

	public CategoriaDTO atualizar(Long id, CategoriaInserirDTO categoriaInserirDTO) {
		categoriaInserirDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Categoria categoria = mapper.map(categoriaInserirDTO, Categoria.class);
		repository.save(categoria);
		return new CategoriaDTO(categoria);
	}

	public Boolean apagar(Long id) {
		Optional<Categoria> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false;
	}

}
