package br.org.serratec.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.dto.ItemPedidoInserirDTO;
import br.org.serratec.dto.ItemPedidoListarDTO;
import br.org.serratec.service.ItemPedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("itens-pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    @ApiOperation(value = "Lista todos as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos as categorias"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<List<ItemPedidoListarDTO>> listarTodos() {
        return ResponseEntity.ok(itemPedidoService.listarTodos());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Lista categorias pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna categoria do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<ItemPedidoDTO> listarPorId(@PathVariable Long id) {
        ItemPedidoDTO itemPedido = itemPedidoService.listarPorId(id);

        if (itemPedido != null) {
            return ResponseEntity.ok(itemPedido);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Cadastrado uma nova categoria", notes = "preencha com os dados da categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o categoria cadastrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<Object> cadastrar(@Valid @RequestBody ItemPedidoInserirDTO itemPedido) {
        ItemPedidoDTO itemPedidoDTO = itemPedidoService.cadastrar(itemPedido);

        if (itemPedidoDTO != null) {

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(itemPedidoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(itemPedidoDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
}
