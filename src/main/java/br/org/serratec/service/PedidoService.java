package br.org.serratec.service;

import java.lang.module.FindException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.dto.PedidoInserirDTO;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Pedido;
import br.org.serratec.repository.ClienteRepository;
import br.org.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
    private ClienteRepository clienteRepository;

	@Transactional
	public List<PedidoDTO> listarTodos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDTO> pedidoDTO = new ArrayList<PedidoDTO>();

		for (Pedido pedido: pedidos) {
			pedidoDTO.add(new PedidoDTO(pedido));
		}

		return pedidoDTO;
	}

	@Transactional
	public PedidoDTO listarPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        
        if (!pedido.isPresent()) {
            return null;
        }

        return new PedidoDTO(pedido.get());
    }

	public PedidoDTO cadastrar(PedidoInserirDTO pedido) {
		Optional<Cliente> cliente = clienteRepository.findById(pedido.getCliente().getId());
		
		if (!cliente.isPresent()) {
            throw new FindException("Id de cliente não encontrado");
        }

		Pedido novoPedido = new Pedido();
		novoPedido.setDataPedido(LocalDate.now());
		novoPedido.setDataEnvio(pedido.getDataEnvio());
		novoPedido.setDataEntrega(pedido.getDataEntrega());
		novoPedido.setStatus(pedido.getStatus());
		novoPedido.setCliente(cliente.get());
		novoPedido = pedidoRepository.save(novoPedido);

		return new PedidoDTO(novoPedido);
	}

	public PedidoDTO atualizar(Long id, PedidoInserirDTO pedido) {
		Optional<Cliente> cliente = clienteRepository.findById(pedido.getCliente().getId());
		
        if (!cliente.isPresent()) {
            throw new FindException("Id de cliente não encontrado");
        }
		
        pedido.setId(id);
        
		Pedido novoPedido = new Pedido();
		novoPedido.setId(pedido.getId());
		novoPedido.setDataPedido(LocalDate.now());
		novoPedido.setDataEnvio(pedido.getDataEnvio());
		novoPedido.setDataEntrega(pedido.getDataEntrega());
		novoPedido.setStatus(pedido.getStatus());
		novoPedido.setCliente(cliente.get());
		novoPedido = pedidoRepository.save(novoPedido);

		return new PedidoDTO(novoPedido);
	}

	public Boolean apagar(Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
		if (pedido.isPresent()) {
			pedidoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
