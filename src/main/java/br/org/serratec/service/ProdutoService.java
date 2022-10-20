package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.ClienteRepository;

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

	public ProdutoDTO cadastrar(ProdutoDTO produtoDTO) {
		produtoDTO.setId(null);
		ModelMapper mapper = new ModelMapper();
		Produto produto = mapper.map(produtoDTO, Produto.class);
		produto = repository.save(produto);
		produtoDTO.setId(produto.getId());
		return produtoDTO;
	}

	public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
		produtoDTO.setId(id);
		ModelMapper mapper = new ModelMapper();
		Produto produto = mapper.map(produtoDTO, Produto.class);
		repository.save(produto);
		return produtoDTO;
	}

	public void apagar(Long id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isEmpty()) {
			// Lançar uma exception
		}
		repository.deleteById(id);
	}

}
