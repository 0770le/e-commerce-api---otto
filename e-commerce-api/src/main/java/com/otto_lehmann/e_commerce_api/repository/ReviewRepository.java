package com.otto_lehmann.e_commerce_api.repository;

import com.otto_lehmann.e_commerce_api.model.Review; // Importa a classe Review
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository
import org.springframework.stereotype.Repository; // Importa a anotação @Repository
import java.util.List;

@Repository // Anotação que indica que esta interface é um componente de acesso a dados (repositório)
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Métodos CRUD básicos para Review já são fornecidos pelo JpaRepository
    List<Review> findByProductId(Long productId);
}