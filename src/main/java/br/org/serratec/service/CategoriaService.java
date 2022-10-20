package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.model.Categoria;
import br.org.serratec.model.Cliente;
import br.org.serratec.repository.ClienteRepository;

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

	public CategoriaDTO cadastrar(CategoriaDTO categoriaDTO) {
		categoriaDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
		categoria = repository.save(categoria);
		categoriaDTO.setId(categoria.getIdCategoria());
		return categoriaDTO;
	}

	public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO) {
		categoriaDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
		repository.save(categoria);
		return categoriaDTO;
	}

	public void apagar(Long id) {
		Optional<Categoria> categoria = repository.findById(id);
		if (categoria.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
