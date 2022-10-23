package br.org.serratec.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

	@GetMapping
    @ApiOperation(value = "Lista todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os pedidos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<List<PedidoDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@GetMapping("{id}")
    @ApiOperation(value = "Lista pedido pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna pedido do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<PedidoDTO> listarPorId(@PathVariable Long id){
		PedidoDTO pedidoDTO = service.listarPorId(id);

		if (pedidoDTO != null) {
		    return ResponseEntity.ok(service.listarPorId(id));
		}

        return ResponseEntity.notFound().build();
	}

	@PostMapping
    @ApiOperation(value = "Cadastrado uma nova pedido", notes = "preencha com os dados da pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o pedido cadastrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<PedidoDTO> cadastrar(@RequestBody PedidoDTO pedido) {
		PedidoDTO pedidoDTO = service.cadastrar(pedido);
		if (pedidoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pedidoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(pedidoDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	

	@PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo pedido", notes = "preencha com os dados do pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o pedido cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<PedidoDTO> atualizar(@PathVariable Long id, @RequestBody PedidoDTO pedido) {
		PedidoDTO pedidoDTO = service.atualizar(id, pedido);		
        if (pedidoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pedidoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(pedidoDTO);
        } 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um pedido", notes = "preencha com o id do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<?> apagar(@PathVariable Long id){
		Boolean response = service.apagar(id);
        if (response != true) {
            return ResponseEntity.notFound().build();
        }
	
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
