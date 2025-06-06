package com.otto_lehmann.e_commerce_api.repository;

import com.otto_lehmann.e_commerce_api.model.Product; // Importa a classe Product
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository
import org.springframework.stereotype.Repository; // Importa a anotação @Repository

@Repository // Anotação que indica que esta interface é um componente de acesso a dados (repositório)
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Métodos CRUD básicos (save, findById, findAll, delete, etc.) já são fornecidos pelo JpaRepository
    // Você pode adicionar métodos de consulta personalizados aqui no futuro, se precisar
}