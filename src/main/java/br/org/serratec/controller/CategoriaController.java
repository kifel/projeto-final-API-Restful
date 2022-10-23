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

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.service.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService service;

    @ApiOperation(value = "Lista todos as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos as categorias"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    @GetMapping
	public ResponseEntity<List<CategoriaDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@ApiOperation(value = "Lista categorias pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna categoria do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    @GetMapping("{id}")
	public ResponseEntity<CategoriaDTO> listarPorId(@PathVariable Long id){
		CategoriaDTO categoriaDTO = service.listarPorId(id);

        if (categoriaDTO != null) {
		    return ResponseEntity.ok(service.listarPorId(id));
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
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<CategoriaDTO> cadastrar(@RequestBody CategoriaDTO categoria) {
		CategoriaDTO categoriaDTO = service.cadastrar(categoria);
        if (categoriaDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(categoriaDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(categoriaDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo categoria", notes = "preencha com os dados do categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o cliente cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoria) {
		CategoriaDTO categoriaDTO = service.atualizar(id, categoria);		
        if (categoriaDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(categoriaDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(categoriaDTO);
        } 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um categoria", notes = "preencha com o id do categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
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
