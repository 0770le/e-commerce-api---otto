package com.otto_lehmann.e_commerce_api.controller; // PACOTE ATUALIZADO

import com.otto_lehmann.e_commerce_api.model.Review; // Importa a entidade Review
import com.otto_lehmann.e_commerce_api.service.ReviewService; // Importa o ReviewService
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.http.HttpStatus; // Para códigos de status HTTP
import org.springframework.http.ResponseEntity; // Para retornar respostas HTTP personalizadas
import org.springframework.web.bind.annotation.*; // Importa todas as anotações REST (@GetMapping, @PostMapping, etc.)

import java.util.List; // Para listas de avaliações
import java.util.Optional; // Para lidar com Optional

@RestController // Indica que esta classe é um controlador REST
// Não definiremos um @RequestMapping base aqui para poder criar caminhos mais flexíveis para Reviews.
public class ReviewController {

    private final ReviewService reviewService; // Injeta o ReviewService

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Endpoint para CRIAR uma nova avaliação para um produto específico (CREATE)
    // POST http://localhost:8080/products/{productId}/reviews
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> createReviewForProduct(
            @PathVariable Long productId,
            @RequestBody Review review) {
        Review savedReview = reviewService.saveReview(productId, review);
        if (savedReview != null) {
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED); // Retorna 201 Created
        } else {
            // Isso aconteceria se o productId não existisse no ProductService.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }

    // Endpoint para LISTAR todas as avaliações de um produto específico (READ ALL for a Product)
    // GET http://localhost:8080/products/{productId}/reviews
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        if (!reviews.isEmpty() || reviewService.getReviewsByProductId(productId) != null) { // Check if product exists implicitly
            return new ResponseEntity<>(reviews, HttpStatus.OK); // Retorna 200 OK
        } else {
            // Se o produto não existir ou não tiver reviews, ainda pode ser OK, mas 404 se o produto não for encontrado
            // Para simplicidade, assumimos que o serviço já lidaria com isso ou que o productId é válido.
            // Aqui, se o produto não existe, o serviço retornaria uma lista vazia, que ainda é 200 OK.
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
    }


    // Endpoint para BUSCAR uma avaliação por ID (READ BY ID - Global)
    // GET http://localhost:8080/reviews/{id}
    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Se encontrar, retorna 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não, retorna 404 Not Found
    }

    // Endpoint para ATUALIZAR uma avaliação por ID (UPDATE)
    // PUT http://localhost:8080/reviews/{id}
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(id, reviewDetails);
        if (updatedReview != null) {
            return new ResponseEntity<>(updatedReview, HttpStatus.OK); // Retorna 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found se não existir
        }
    }

    // Endpoint para DELETAR uma avaliação por ID (DELETE)
    // DELETE http://localhost:8080/reviews/{id}
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
}