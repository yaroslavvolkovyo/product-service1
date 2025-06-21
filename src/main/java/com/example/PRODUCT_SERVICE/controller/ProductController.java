package com.example.PRODUCT_SERVICE.controller;

import com.example.PRODUCT_SERVICE.dto.ProductDto;
import com.example.PRODUCT_SERVICE.entity.Product;
import com.example.PRODUCT_SERVICE.factories.ProductDtoFactory;
import com.example.PRODUCT_SERVICE.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductDtoFactory productDtoFactory;
    private final ProductService productService;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = productDtoFactory.createProduct(productDto);
        productService.createProduct(product);
        return productDto;
    }
//todo добавить кэширование
    @GetMapping
    public List<ProductDto> findAllProducts(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return productService.findProductByName(name).stream().map(productDtoFactory::createProductDto).collect(Collectors.toList());
        }else {
            return productService.findAll()
                    .stream()
                    .map(product -> productDtoFactory.createProductDto(product))
                    .toList();
        }
    }




    @GetMapping("/category/{category}")
    public List<ProductDto> findProductsByCategory(@PathVariable String category, @RequestParam(required = false) String sort,@RequestParam(required = false) Integer minPrice,@RequestParam(required = false) Integer maxPrice) {
        return productService.findProductByCategory(category,sort,minPrice,maxPrice).stream().map(productDtoFactory::createProductDto).collect(Collectors.toList());
    }

    @GetMapping("/{article}")
    public ProductDto findProductByArticle(@PathVariable Long article) {
        Product product = productService.findProductByArticleNumber(article);
        return productDtoFactory.createProductDto(product);
    }


}
