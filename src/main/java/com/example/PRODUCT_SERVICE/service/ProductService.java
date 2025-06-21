package com.example.PRODUCT_SERVICE.service;

import com.example.PRODUCT_SERVICE.category.Category;
import com.example.PRODUCT_SERVICE.dto.InventoryDto;
import com.example.PRODUCT_SERVICE.dto.ProductDto;
import com.example.PRODUCT_SERVICE.entity.Product;
import com.example.PRODUCT_SERVICE.factories.ProductDtoFactory;
import com.example.PRODUCT_SERVICE.feignClient.InventoryServiceClient;
import com.example.PRODUCT_SERVICE.kafka.ProductEventProducer;
import com.example.PRODUCT_SERVICE.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final ProductEventProducer productEventProducer;

        public Product createProduct(Product product) {

            Product savedProduct = productRepository.save(product);
            productEventProducer.sendProductCreatedEvent(product);
            return savedProduct;
        }

        public List<Product> findAll() {
            return productRepository.findAll();
        }

        public List<Category> findAllCategory() {
            return new ArrayList<>(Arrays.asList(Category.values()));
        }

        public List<Product> findProductByName(String name) {
            return productRepository.findProductsByTitle(name);
        }

        public Product findProductByArticleNumber(Long article) {

            Product product = productRepository.findProductsByProductArticle(article);
            product.setQuantity(inventoryServiceClient.getQuantity(article));
            return product;
        }



        public List<Product> findProductByCategory(String category, String sort, Integer minPrice, Integer maxPrice) {
            List<Product> products;
            if(category == null || category.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category");
            }
            if(minPrice == null) {
                minPrice = 0;
            }
            if (maxPrice == null) {
                maxPrice = Integer.MAX_VALUE;
            }
            if(minPrice>maxPrice) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid min price");
            }
            if (sort != null && sort.equalsIgnoreCase("asc")) {
                products = productRepository.findByCategoryAndPriceBetweenOrderByPriceAsc(category, minPrice, maxPrice);
            } else if (sort != null && sort.equalsIgnoreCase("desc")) {
                products = productRepository.findByCategoryAndPriceBetweenOrderByPriceDesc(category, minPrice, maxPrice);
            } else if (sort != null&& !sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort");
            } else{
                products = productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
            }
            if (products.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found with category " + category + "for given price range");
            }
            return products;
        }


}
