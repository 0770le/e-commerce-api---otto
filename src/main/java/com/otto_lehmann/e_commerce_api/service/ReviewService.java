package com.otto_lehmann.e_commerce_api.service;

import com.otto_lehmann.e_commerce_api.model.Product; // Importa a entidade Product
import com.otto_lehmann.e_commerce_api.model.Review; // Importa a entidade Review
import com.otto_lehmann.e_commerce_api.repository.ReviewRepository; // Importa o ReviewRepository
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.stereotype.Service; // Anotação para indicar que é uma classe de serviço
import java.util.List; // Para retornar listas de avaliações
import java.util.Optional; // Para lidar com avaliações que podem ou não existir

@Service // Indica que esta classe é um componente de serviço gerenciado pelo Spring
public class ReviewService {

    private final ReviewRepository reviewRepository; // Repositório para operações de Review
    private final ProductService productService; // Serviço para buscar o Produto associado

    @Autowired // Injeta os repositórios e serviços automaticamente pelo Spring
    public ReviewService(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    // --- Métodos CRUD para Review ---

    // Criar/Salvar uma nova avaliação para um produto específico
    public Review saveReview(Long productId, Review review) {
        Optional<Product> productOptional = productService.getProductById(productId); // Tenta encontrar o produto
        if (productOptional.isPresent()) {
            review.setProduct(productOptional.get()); // Associa a avaliação ao produto encontrado
            return reviewRepository.save(review);
        } else {
            // Se o produto não for encontrado, a avaliação não pode ser salva.
            // Em uma aplicação real, você lançaria uma exceção customizada aqui (ex: ProductNotFoundException).
            return null; // Por simplicidade, retorna null por enquanto.
        }
    }

    // Listar todas as avaliações
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Buscar uma avaliação por ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // Atualizar uma avaliação existente
    public Review updateReview(Long id, Review reviewDetails) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            existingReview.setRating(reviewDetails.getRating());
            existingReview.setComment(reviewDetails.getComment());
            existingReview.setReviewerName(reviewDetails.getReviewerName());
            // O produto associado não deve ser alterado em uma atualização simples de Review
            return reviewRepository.save(existingReview);
        } else {
            return null; // Não encontrou a avaliação para atualizar.
        }
    }

    // Deletar uma avaliação por ID
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    // --- Método adicional para buscar avaliações por produto (muito comum para One-to-Many) ---
    // Este método buscará as avaliações de um produto específico.
    // Ele exigirá uma pequena adição na interface ReviewRepository no próximo passo.
    public List<Review> getReviewsByProductId(Long productId) {
        // Por enquanto, esta linha causaria um erro de compilação até adicionarmos o método no ReviewRepository
        // return reviewRepository.findByProductId(productId);
        // Retornando uma lista vazia por enquanto para não travar
        return reviewRepository.findByProductId(productId); // <-- Essa linha será a correta.
    }
}