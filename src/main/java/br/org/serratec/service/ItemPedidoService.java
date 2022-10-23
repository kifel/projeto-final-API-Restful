package br.org.serratec.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.dto.ItemPedidoInserirDTO;
import br.org.serratec.dto.ItemPedidoListarDTO;
import br.org.serratec.dto.ProdutoItemPedidoListDTO;
import br.org.serratec.exception.PedidoException;
import br.org.serratec.exception.ProdutoException;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.ItemPedidoRepository;
import br.org.serratec.repository.PedidoRepository;
import br.org.serratec.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public ItemPedidoDTO InserirUrlImagemItem(ItemPedido itemPedido) {

        Optional<Produto> produtos = produtoRepository.findById(itemPedido.getProduto().getId());

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produtos/foto/{id}")
                .buildAndExpand(produtos.get().getId()).toUri();

        ProdutoItemPedidoListDTO produtoUrl = new ProdutoItemPedidoListDTO();
        produtoUrl.setId(produtos.get().getId());
        produtoUrl.setCategoria(produtos.get().getCategoria());
        produtoUrl.setDataCadastro(produtos.get().getDataCadastro());
        produtoUrl.setDescricao(produtos.get().getDescricao());
        produtoUrl.setNome(produtos.get().getNome());
        produtoUrl.setQtdEstoque(produtos.get().getQtdEstoque());
        produtoUrl.setValorUnitario(produtos.get().getValorUnitario());
        produtoUrl.setLinkImagem(uri.toString());

        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(itemPedido);
        itemPedidoDTO.setId(itemPedido.getId());
        itemPedidoDTO.setProduto(produtoUrl);

        return itemPedidoDTO;

    }

    public List<ItemPedidoListarDTO> listarTodos() {
        List<ItemPedido> items = itemPedidoRepository.findAll();
        List<ItemPedidoListarDTO> itemDTO = new ArrayList<>();


        for (ItemPedido item : items) {
            
            itemDTO.add(new ItemPedidoListarDTO(item));
        }

        return itemDTO;
    }

    public ItemPedidoDTO listarPorId(Long id) {
        Optional<ItemPedido> itemPedido = itemPedidoRepository.findById(id);
        System.out.println(itemPedido.get().getPedido().getId());

        if (!itemPedido.isPresent()) {
            return null;
        }

        return new ItemPedidoDTO(itemPedido.get());
    }

   public ItemPedidoDTO cadastrar(ItemPedidoInserirDTO itemPedido) {

        if (pedidoRepository.findById(itemPedido.getPedido().getId()) == null) {
            throw new PedidoException("Pedido não encontrado !");
        }

        if (produtoRepository.findById(itemPedido.getProduto().getIdProduto()) == null) {
            throw new ProdutoException("Produto não encontrado !");
        }

        Double subTotal = 0.0;

        List<Produto> produtosListados = produtoRepository.findAll();

        for (Produto produtos : produtosListados) {
            subTotal += produtos.getValorUnitario() * produtos.getQtdEstoque();
        }

        Optional<Pedido> pedido = pedidoRepository.findById(itemPedido.getPedido().getId());
        Optional<Produto> produtos = produtoRepository.findById(itemPedido.getProduto().getIdProduto());

        ItemPedido item = new ItemPedido();
        item.setQuantidade(itemPedido.getQuantidade());
        item.setPrecoVenda(subTotal);
        item.setPedido(pedido.get());
        item.setProduto(produtos.get());
        item = itemPedidoRepository.save(item);

        return InserirUrlImagemItem(item);
    }

    public ItemPedidoDTO atualizar(ItemPedido itemPedido, Long id) {

        itemPedido.setId(id);

        Double subTotal = 0.0;

        List<Produto> produtosListados = produtoRepository.findAll();

        for (Produto produtos : produtosListados) {
            subTotal += produtos.getValorUnitario() * produtos.getQtdEstoque();
        }

        System.out.println(subTotal);

        ItemPedido item = new ItemPedido();
        item.setId(itemPedido.getId());
        item.setQuantidade(itemPedido.getQuantidade());
        item.setPrecoVenda(subTotal);
        item.setPedido(itemPedido.getPedido());
        item.setProduto(itemPedido.getProduto());

        return new ItemPedidoDTO(item);
    }

    public Boolean apagar(Long id) {

        Optional<ItemPedido> itemPedido = itemPedidoRepository.findById(id);

        if (itemPedido.isPresent()) {
            itemPedidoRepository.deleteById(id);
            return true;
        }

        return false;

    }

}
