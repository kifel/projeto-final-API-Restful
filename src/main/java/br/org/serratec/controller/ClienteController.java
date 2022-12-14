package br.org.serratec.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import br.org.serratec.dto.ClienteDTO;
import br.org.serratec.dto.ClienteInserirDTO;
import br.org.serratec.dto.ClienteListDTO;
import br.org.serratec.exception.CpfException;
import br.org.serratec.exception.EmailException;
import br.org.serratec.exception.NomeUsuarioException;
import br.org.serratec.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@ApiOperation(value = "Lista todos os clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos os clientes"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<List<ClienteListDTO>> listar() {
		return ResponseEntity.ok(clienteService.listar());
	}

	@GetMapping("{id}")
	@ApiOperation(value = "Lista cliente pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna cliente do id referenciado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<ClienteListDTO> buscar(@PathVariable Long id) {
		ClienteListDTO cliente = clienteService.buscar(id);

		if (cliente != null) {
			return ResponseEntity.ok(cliente);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation(value = "Cadastrado um novo cliente", notes = "preencha com os dados do clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o cliente cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<Object> inserir(@Valid @RequestBody ClienteInserirDTO cliente) {
		try {
			ClienteDTO clienteDTO = clienteService.inserir(cliente);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteDTO.getId())
					.toUri();
			return ResponseEntity.created(uri).body(clienteDTO);
		} catch (EmailException | NomeUsuarioException | CpfException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Cadastrado um novo cliente", notes = "preencha com os dados do clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o cliente cadastrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "422", description = "Você credencias já cadastradas no banco de dados"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<Object> atualizar(@Valid @RequestBody ClienteInserirDTO cliente, @PathVariable Long id) {

		ClienteListDTO c = clienteService.buscar(id);

		if (c != null) {
			try {
				ClienteDTO clienteDTO = clienteService.atualizar(id, cliente);
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(clienteDTO.getId())
						.toUri();
				return ResponseEntity.created(uri).body(clienteDTO);
			} catch (EmailException | NomeUsuarioException | CpfException e) {
				return ResponseEntity.unprocessableEntity().body(e.getMessage());
			}
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "Deleta um cliente", notes = "preencha com o id do cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro na aplicação")
	})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Boolean response = clienteService.delete(id);
		if (response != true) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
