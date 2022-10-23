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

import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    
    @Autowired
    private EnderecoService service;

    @ApiOperation(value = "Lista todos as enderecos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos as enderecos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    @GetMapping
	public ResponseEntity<List<EnderecoDTO>> listarTodos(){		
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@ApiOperation(value = "Lista enderecos pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna endereco do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    @GetMapping("{id}")
	public ResponseEntity<EnderecoDTO> listarPorId(@PathVariable Long id){
		EnderecoDTO enderecoDTO = service.listarPorId(id);

        if (enderecoDTO != null) {
		    return ResponseEntity.ok(service.listarPorId(id));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Cadastrado uma nova endereco", notes = "preencha com os dados da endereco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o endereco cadastrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<EnderecoDTO> cadastrar(@RequestBody EnderecoDTO endereco) {
		EnderecoDTO enderecoDTO = service.cadastrar(endereco);
        if (enderecoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(enderecoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(enderecoDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo endereco", notes = "preencha com os dados do endereco")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o endereco cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<EnderecoDTO> atualizar(@PathVariable Long id, @RequestBody EnderecoDTO endereco) {
		EnderecoDTO enderecoDTO = service.atualizar(id, endereco);		
        if (enderecoDTO != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(enderecoDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(enderecoDTO);
        } 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um endereco", notes = "preencha com o id do endereco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
	public ResponseEntity<EnderecoDTO> apagar(@PathVariable Long id){
        Boolean response = service.apagar(id);
        if (response != true) {
            return ResponseEntity.notFound().build();
        }
	
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
