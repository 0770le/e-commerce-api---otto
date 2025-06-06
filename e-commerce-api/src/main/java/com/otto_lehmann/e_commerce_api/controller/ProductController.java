package com.otto_lehmann.e_commerce_api.controller; // PACOTE ATUALIZADO

import com.otto_lehmann.e_commerce_api.model.Product; // Importa a entidade Product
import com.otto_lehmann.e_commerce_api.service.ProductService; // Importa o ProductService
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.http.HttpStatus; // Para códigos de status HTTP
import org.springframework.http.ResponseEntity; // Para retornar respostas HTTP personalizadas
import org.springframework.web.bind.annotation.*; // Importa todas as anotações REST (@GetMapping, @PostMapping, etc.)

import java.util.List; // Para listas de produtos
import java.util.Optional; // Para lidar com Optional

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/products") // Define o caminho base para todos os endpoints deste controlador (ex: /products)
public class ProductController {

    private final ProductService productService; // Injeta o ProductService

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint para CRIAR um novo produto (CREATE)
    // POST http://localhost:8080/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); // Retorna 201 Created
    }

    // Endpoint para LISTAR todos os produtos (READ ALL)
    // GET http://localhost:8080/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para BUSCAR um produto por ID (READ BY ID)
    // GET http://localhost:8080/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Se encontrar, retorna 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não, retorna 404 Not Found
    }

    // Endpoint para ATUALIZAR um produto por ID (UPDATE)
    // PUT http://localhost:8080/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // Retorna 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found se não existir
        }
    }

    // Endpoint para DELETAR um produto por ID (DELETE)
    // DELETE http://localhost:8080/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
}