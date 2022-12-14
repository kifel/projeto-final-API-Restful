package br.org.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoDTO buscar(String cep, String complemento, Integer numero) {

        //NOTE: Consulta o cep no viaCep e retorna o objeto preenchido
        RestTemplate rs = new RestTemplate();
        String uri = "http://viacep.com.br/ws/" + cep + "/json";
        Optional<Endereco> enderecoViaCep = Optional.ofNullable(rs.getForObject(uri, Endereco.class));

        //NOTE: Verifica ser o cep é valido pela resposta do via cep
        if (enderecoViaCep.get().getCep() != null) {

            //NOTE: Retira os traços do cep
            String cepSemTraco = enderecoViaCep.get().getCep().replaceAll("-", "");
            enderecoViaCep.get().setCep(cepSemTraco);

            //NOTE: Set o complemente e número inseridos pelo cliente no cadastro
            enderecoViaCep.get().setComplemento(complemento);
            enderecoViaCep.get().setNumero(numero);

            //NOTE: Retorna o endereço
            return new EnderecoDTO(enderecoViaCep.get());
        } else {
            //NOTE: Retorna o 404 Not Found
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public Endereco salvar(String cep, String complemento, Integer numero) {
        //NOTE: Utiliza a função que busca no viaCep
        EnderecoDTO ent = buscar(cep, complemento, numero);

        //NOTE: Salva no banco de dados o endereço
        Endereco endereco = new Endereco();
        endereco.setBairro(ent.getBairro());
        endereco.setCep(ent.getCep());
        endereco.setId(ent.getId());
        endereco.setLogradouro(ent.getLogradouro());
        endereco.setLocalidade(ent.getCidade());
        endereco.setUf(ent.getEstado());
        endereco.setComplemento(ent.getComplemento());
        endereco.setNumero(ent.getNumero());

        endereco = enderecoRepository.save(endereco);

        return endereco;
    }

    public Endereco atualizar(String cep, String complemento, Integer numero, Long id) {
        //NOTE: Utiliza a função que busca no viaCep
        EnderecoDTO ent = buscar(cep, complemento, numero);

        //NOTE: Atualiza no banco de dados o endereço
        ent.setId(id);
        
        Endereco endereco = new Endereco();
        endereco.setId(ent.getId());
        endereco.setBairro(ent.getBairro());
        endereco.setCep(ent.getCep());
        endereco.setId(ent.getId());
        endereco.setLogradouro(ent.getLogradouro());
        endereco.setLocalidade(ent.getCidade());
        endereco.setUf(ent.getEstado());
        endereco.setComplemento(ent.getComplemento());
        endereco.setNumero(ent.getNumero());

        endereco = enderecoRepository.save(endereco);

        return endereco;
    }

    public List<EnderecoDTO> listar() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        List<EnderecoDTO> enderecoDTO = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            enderecoDTO.add(new EnderecoDTO(endereco));
        }

        return enderecoDTO;
    }

    public EnderecoDTO buscarPorId(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);

        if (!endereco.isPresent()) {
            return null;
        }

        return new EnderecoDTO(endereco.get());
    }
}
