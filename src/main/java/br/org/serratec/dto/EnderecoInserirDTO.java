package br.org.serratec.dto;

import br.org.serratec.model.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EnderecoInserirDTO {

    private Integer numero;
    private String complemento;
    private String cep;

    public EnderecoInserirDTO(Endereco endereco) {
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.cep = endereco.getCep();
    }

}
