package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public List<ProdutoDTO> listarTodos() {
		List<Produto> produtos = repository.findAll();
		
		return produtos.stream()
				.map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
				.collect(Collectors.toList());
	}

	public Optional<ProdutoDTO> listarPorId(Long id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isEmpty()) {
			// Lançar uma exception
		}
		ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
		return Optional.of(dto);
	}

	public ProdutoDTO cadastrar(ProdutoInserirDTO produtoInserirDTO) {
		produtoInserirDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Produto produto = mapper.map(produtoInserirDTO, Produto.class);
		produto = repository.save(produto);
		produtoInserirDTO.setId(produto.getId());
		return new ProdutoDTO(produto);
	}

	public ProdutoDTO atualizar(Long id, ProdutoInserirDTO produtoInserirDTO) {
		produtoInserirDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Produto produto = mapper.map(produtoInserirDTO, Produto.class);
		repository.save(produto);
		return new ProdutoDTO(produto);
	}

	public Boolean apagar(Long id) {
		Optional<Produto> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false;
	}

}
