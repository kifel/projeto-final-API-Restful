package br.org.serratec.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.dto.ProdutoInserirDTO;
import br.org.serratec.model.Categoria;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.CategoriaRepository;
import br.org.serratec.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ProdutoDTO inserirUriDaImagem(Produto produto) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produtos/foto/{id}")
				.buildAndExpand(produto.getId()).toUri();

		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setDataCadastro(produto.getDataCadastro());
		dto.setValorUnitario(produto.getValorUnitario());
		dto.setQtdEstoque(produto.getQtdEstoque());
		dto.setCategoria(produto.getCategoria());
		dto.setUri(uri.toString());

		return dto;

	}

	public List<ProdutoDTO> listarTodos() {
		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoDTO> produtoDTO = new ArrayList<>();

		for (Produto produto : produtos) {
			produtoDTO.add(inserirUriDaImagem(produto));
		}

		return produtoDTO;
	}

	public ProdutoDTO listarPorId(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);

		if (!produto.isPresent()) {
			return null;
		}

		return inserirUriDaImagem(produto.get());
	}

	public ProdutoDTO cadastrar(ProdutoInserirDTO produtoDTO, MultipartFile file) throws IOException {

		Optional<Categoria> categoria = categoriaRepository.findById(produtoDTO.getCategoria().getId());

		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setQtdEstoque(produtoDTO.getQtdEstoque());
		produto.setDataCadastro(produtoDTO.getDataCadastro());
		produto.setValorUnitario(produtoDTO.getValorUnitario());
		produto.setCategoria(categoria.get());
		produto.setImagem(file.getBytes());

		produto = produtoRepository.save(produto);

		return inserirUriDaImagem(produto);
	}

	public ProdutoDTO atualizar(Long id, ProdutoInserirDTO dto, MultipartFile file) throws IOException {

		dto.setId(id);
		Optional<Categoria> categoria = categoriaRepository.findById(dto.getCategoria().getId());

		Produto produto = new Produto();

		produto.setId(dto.getId());
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setCategoria(categoria.get());
		produto.setDataCadastro(dto.getDataCadastro());
		produto.setQtdEstoque(dto.getQtdEstoque());
		produto.setValorUnitario(dto.getValorUnitario());
		produto.setImagem(file.getBytes());
		;

		produto = produtoRepository.save(produto);

		return inserirUriDaImagem(produto);
	}

	public Boolean apagar(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			produtoRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public Produto buscarFoto(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (!produto.isPresent()) {
			return null;
		}
		return produto.get();
	}

}
