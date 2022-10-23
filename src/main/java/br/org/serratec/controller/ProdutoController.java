package br.org.serratec.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;

    @ApiOperation(value = "Lista todos as produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos as produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<List< ProdutoDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
    @ApiOperation(value = "Lista produtos pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna produto do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<ProdutoDTO> listarPorId(@PathVariable Long id){
		ProdutoDTO produtoDTO = service.listarPorId(id);

        if (produtoDTO != null) {
		    return ResponseEntity.ok(service.listarPorId(id));
        }

        return ResponseEntity.notFound().build();
    }

	@PostMapping
    @ApiOperation(value = "Cadastrado uma nova produto", notes = "preencha com os dados da produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o produto cadastrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody  ProdutoDTO  produto) {
		ProdutoDTO produtoDTO = service.cadastrar(produto);
        if (produtoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(produtoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(produtoDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo produto", notes = "preencha com os dados do produto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o produto cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody  ProdutoDTO  produto) {
		ProdutoDTO produtoDTO = service.atualizar(id, produto);		
        if (produtoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(produtoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(produtoDTO);
        } 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um produto", notes = "preencha com o id do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
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
