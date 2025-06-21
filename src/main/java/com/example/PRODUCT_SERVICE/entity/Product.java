package com.example.PRODUCT_SERVICE.entity;

import com.example.PRODUCT_SERVICE.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String title;
    private String description;
    private double price;
    private Category category;
    private Long productArticle;
    private int quantity;
}
