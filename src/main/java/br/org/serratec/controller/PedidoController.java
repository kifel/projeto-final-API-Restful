package br.org.serratec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
	public ResponseEntity<List<PedidoDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
    @GetMapping("/{id}")
	public ResponseEntity<Optional<PedidoDTO>> listarPorId(@PathVariable Long id){
		return ResponseEntity.ok(service.listarPorId(id));
	}

    @PostMapping
	public ResponseEntity<PedidoDTO> cadastrar(@RequestBody PedidoDTO pedido) {
		PedidoDTO dto = service.cadastrar(pedido);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

    @PutMapping("/{id}")
	public ResponseEntity<PedidoDTO> atualizar(@PathVariable Long id, @RequestBody PedidoDTO pedido) {
		PedidoDTO dto = service.atualizar(id, pedido);		
		return ResponseEntity.ok(dto);
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<?> apagar(@PathVariable Long id){
		service.apagar(id);
		return ResponseEntity.ok("O id:" + id + " foi deletado com sucesso!"); 
	}
}
