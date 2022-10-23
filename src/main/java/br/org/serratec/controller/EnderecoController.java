package br.org.serratec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    @ApiOperation(value = "Lista todos os enderecos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os enderecos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<List<EnderecoDTO>> listar() {
        return ResponseEntity.ok(enderecoService.listar());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Lista todos os enderecos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os enderecos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "endereco não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<EnderecoDTO> buscar(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.buscarPorId(id);

        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        }

        return ResponseEntity.notFound().build();
    }
    
}