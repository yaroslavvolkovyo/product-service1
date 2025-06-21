package com.example.PRODUCT_SERVICE.dto;

import com.example.PRODUCT_SERVICE.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String title;
    private String description;
    private double price;
    private Category category;
    private Long productArticle;
    private int quantity;
}
