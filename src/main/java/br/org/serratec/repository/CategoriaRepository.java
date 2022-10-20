package br.org.serratec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}