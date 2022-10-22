package br.org.serratec.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    private String email;

    private String nomeUsuario;

    private String nomeCompleto;

    private String senha;

    private String cpf;

    private String telefone;

    @Column(name = "data_nasc")
    private LocalDate dataNascimento;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedido;

    @Override
    public String toString() {
        return "Email: " + email + "\nNome de Usuario: " + nomeUsuario + "\nNome Completo: " + nomeCompleto + "\nCpf: "
                + cpf + "\nTelefone: " + telefone + "\nData de Nascimento: " + dataNascimento;
    }

}