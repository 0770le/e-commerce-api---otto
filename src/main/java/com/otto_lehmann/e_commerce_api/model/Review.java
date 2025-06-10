package com.otto_lehmann.e_commerce_api.model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import lombok.AllArgsConstructor; // Gera construtor com todos os argumentos
import lombok.Data; // Gera getters, setters, toString, equals e hashCode
import lombok.NoArgsConstructor; // Gera construtor sem argumentos

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- Adicione esta importação

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Table(name = "reviews") // Define o nome da tabela no banco de dados
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os argumentos
public class Review {

    @Id // Marca o campo como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática de valor para a chave primária
    private Long id;

    @Column(nullable = false) // A nota não pode ser nula
    private Integer rating; // A pontuação da avaliação

    @Column(columnDefinition = "TEXT") // Mapeia para uma coluna de texto longo
    private String comment; // O texto da avaliação

    @Column(nullable = false, length = 100) // O nome do avaliador não pode ser nulo
    private String reviewerName;

    // --- Relacionamento Many-to-One com Product ---
    @ManyToOne // Indica um relacionamento muitos para um (muitas Reviews para um Produto)
    @JoinColumn(name = "product_id", nullable = false) // Define a coluna da chave estrangeira na tabela 'reviews'
    @JsonBackReference
    private Product product; // A avaliação pertence a um único Produto
}