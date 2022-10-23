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

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.service.ItemPedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/itempedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService service;

    @GetMapping
	@ApiOperation(value = "Lista todos os itens pedidos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos os itens pedidos"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "404", description = "Item pedido não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<List<ItemPedidoDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@GetMapping("{id}")
	@ApiOperation(value = "Lista item pedido pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna item pedido do id referenciado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "404", description = "Item pedido não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<ItemPedidoDTO> listarPorId(@PathVariable Long id){
		ItemPedidoDTO itemPedidoDTO = service.listarPorId(id);

        if (itemPedidoDTO != null) {
		    return ResponseEntity.ok(service.listarPorId(id));
        }

        return ResponseEntity.notFound().build();
    }

	@PostMapping
    @ApiOperation(value = "Cadastrado uma nova item pedido", notes = "preencha com os dados da item pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o item pedido cadastrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
            @ApiResponse(responseCode = "404", description = "Item pedido  não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<ItemPedidoDTO> cadastrar(@RequestBody ItemPedidoDTO itemPedido) {
		ItemPedidoDTO itemPedidoDTO = service.cadastrar(itemPedido);
        if (itemPedidoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(itemPedidoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(itemPedidoDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo item pedido ", notes = "preencha com os dados do item pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o item pedido cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Item pedido  não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<ItemPedidoDTO> atualizar(@PathVariable Long id, @RequestBody ItemPedidoDTO itemPedido) {
		ItemPedidoDTO itemPedidoDTO = service.atualizar(id, itemPedido);		
        if (itemPedidoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(itemPedidoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(itemPedidoDTO);
        } 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}


    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um item pedido", notes = "preencha com o id do item pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Item pedido não encontrado"),
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
