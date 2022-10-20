package br.org.serratec.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import br.org.serratec.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}