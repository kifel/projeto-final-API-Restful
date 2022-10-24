package br.org.serratec.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.dto.ProdutoInserirDTO;
import br.org.serratec.model.Produto;
import br.org.serratec.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @ApiOperation(value = "Lista todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<List<ProdutoDTO>> listar() {
        return ResponseEntity.ok(produtoService.listar());
    }

    @ApiOperation(value = "Lista produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna produto do id referenciado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscar(id);

        if (produto != null) {
            return ResponseEntity.ok(produto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Cadastrado um novo produto", notes = "preencha com os dados do produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Imagem não foi selecionada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "415", description = "Tipo de arquivo não suportado"),
            @ApiResponse(responseCode = "422", description = "Categoria não encontrada ou arquivo invalido"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<Object> inserir(@RequestParam("file") MultipartFile file,
            @Valid @RequestPart ProdutoInserirDTO produto) throws IOException {

        try {
            ProdutoDTO produtoDTO = produtoService.inserir(produto, file);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(produtoDTO.getId()).toUri();

            return ResponseEntity.created(uri).body(produtoDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Cadastrado um novo produto", notes = "preencha com os dados do produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Imagem não foi selecionada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "415", description = "Tipo de arquivo não suportado"),
            @ApiResponse(responseCode = "422", description = "Categoria não encontrada ou arquivo invalido"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<Object> atualizar(@RequestParam("file") MultipartFile file,
            @Valid @RequestPart ProdutoInserirDTO produto, @PathVariable Long id) throws IOException {

        ProdutoDTO p = produtoService.buscar(id);

        if (p != null) {
            try {
                ProdutoDTO produtoDTO = produtoService.atualizar(id, produto, file);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(produtoDTO.getId()).toUri();

                return ResponseEntity.created(uri).body(produtoDTO);
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um produto", notes = "preencha com o id do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Boolean response = produtoService.delete(id);
        System.out.println(response);
        if (response != true) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/foto/{id}")
    @ApiOperation(value = "Mostra a foto do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a foto do produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para o recurso"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na aplicação")
    })
    public ResponseEntity<byte[]> buscarPorFoto(@PathVariable Long id) {

        Produto foto = produtoService.buscarFoto(id);

        if (foto != null) {
            HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
            httpHeaders.add("content-type", "image/png");
            httpHeaders.add("content-length", String.valueOf(foto.getImagem().length));
            return new ResponseEntity<>(foto.getImagem(), httpHeaders, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
