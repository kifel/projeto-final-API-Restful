package br.org.serratec.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.serratec.exception.EnumValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public enum Status {

    PENDENTE("1"), POSTADO("2"), EM_ROTA("3"), ENTREGUE("4");

    @Setter
    @Getter
    private String status;
    
    @JsonCreator
    public static Status verifica(String valor) throws EnumValidationException {
        for (Status estados : Status.values()) {
            if (valor.equals(estados.getStatus())) {
                return estados;
            }
        }
        throw new EnumValidationException("Preencha o Estados corretamente");
    }
}
