package br.org.serratec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.model.Produto;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
    
}

