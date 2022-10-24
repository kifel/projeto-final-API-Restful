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

	public CategoriaDTO listarPorId(Long id) {
        Optional<Categoria> categoria = repository.findById(id);
        if (!categoria.isPresent()) {
            return null;
        }

        return new CategoriaDTO(categoria.get());
    }

	public CategoriaDTO cadastrar(Categoria categoria) {

		Categoria novaCategoria = new Categoria();
		novaCategoria.setNome(categoria.getNome());
		novaCategoria.setDescricao(categoria.getDescricao());
        repository.save(novaCategoria);

		return new CategoriaDTO(novaCategoria);
	}

	public CategoriaDTO atualizar(Long id, Categoria categoria) {
		categoria.setId(id);
		Categoria novaCategoria = new Categoria();
		novaCategoria.setId(categoria.getId());
		novaCategoria.setNome(categoria.getNome());
		novaCategoria.setDescricao(categoria.getDescricao());
        repository.save(novaCategoria);
		
		return new CategoriaDTO(categoria);
	}

	public Boolean apagar(Long id) {
		Optional<Categoria> categoria = repository.findById(id);
		if (categoria.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false;
	}

}
