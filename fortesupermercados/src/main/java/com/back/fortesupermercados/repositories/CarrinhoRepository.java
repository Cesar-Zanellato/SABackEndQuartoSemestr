package com.back.fortesupermercados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.fortesupermercados.entities.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{

    
}
