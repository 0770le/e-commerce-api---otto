package com.otto_lehmann.e_commerce_api.service;

import com.otto_lehmann.e_commerce_api.model.Product; // Importa a entidade Product
import com.otto_lehmann.e_commerce_api.repository.ProductRepository; // Importa o ProductRepository
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.stereotype.Service; // Anotação para indicar que é uma classe de serviço
import java.util.List; // Para retornar listas de produtos
import java.util.Optional; // Para lidar com produtos que podem ou não existir

@Service // Indica que esta classe é um componente de serviço gerenciado pelo Spring
public class ProductService {

    private final ProductRepository productRepository; // Declara o repositório como final para imutabilidade

    @Autowired // Injeta o ProductRepository automaticamente pelo Spring
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // --- Métodos CRUD para Product ---

    // Criar/Salvar um novo produto
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Listar todos os produtos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Buscar um produto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Atualizar um produto existente
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            // Se você tiver mais campos para atualizar, adicione-os aqui
            return productRepository.save(existingProduct);
        } else {
            // Poderíamos lançar uma exceção ou retornar null/Optional.empty() para indicar que não encontrou
            return null; // Por simplicidade, vamos retornar null por enquanto. Em apps reais, lance NotFoundException.
        }
    }

    // Deletar um produto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}