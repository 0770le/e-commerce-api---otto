package com.otto_lehmann.e_commerce_api.model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import lombok.AllArgsConstructor; // Gera construtor com todos os argumentos
import lombok.Data; // Gera getters, setters, toString, equals e hashCode
import lombok.NoArgsConstructor; // Gera construtor sem argumentos
import java.util.List; // Importa a interface List
import java.util.ArrayList; // Importa a classe ArrayList (para inicialização)

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Table(name = "products") // Define o nome da tabela no banco de dados (opcional, se não especificado usa o nome da classe)
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os argumentos
public class Product {

    @Id // Marca o campo como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática de valor para a chave primária (auto-incremento)
    private Long id; // Tipo Long para IDs autoincrementais

    @Column(nullable = false, length = 100) // Mapeia para uma coluna, não permite nulos e define tamanho máximo de 100 caracteres
    private String name;

    @Column(columnDefinition = "TEXT") // Mapeia para uma coluna de texto longo
    private String description;

    @Column(nullable = false) // Mapeia para uma coluna, não permite nulos
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // Inicializa a lista para evitar NullPointerException
}