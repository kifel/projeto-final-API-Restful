package br.org.serratec.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Cliente findByEmail(String email);

    Cliente findByNomeUsuario(String nomeUsuario);

    Cliente findByCpf(String cpf);

}
