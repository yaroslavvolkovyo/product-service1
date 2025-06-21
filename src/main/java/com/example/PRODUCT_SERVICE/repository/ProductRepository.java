package com.example.PRODUCT_SERVICE.repository;

import com.example.PRODUCT_SERVICE.category.Category;
import com.example.PRODUCT_SERVICE.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findProductsByCategory(String category);//простой поиск по категории
    List<Product> findProductsByCategoryOrderByPriceAsc(String category);//поиск по категории, который возвращает в порядке возрастания
    List<Product> findProductsByCategoryOrderByPriceDesc(String category);//поиск по категории, который возвращает в порядке убывания

    List<Product> findProductsByTitle(String title);

    Product findProductsByProductArticle(Long productArticle);
    List<Product> findProductsByCategoryAndPriceGreaterThan(String category, Integer price);//поиск по категории и цене которая больше чем price
    List<Product> findProductsByCategoryAndPriceLessThan(String category, Integer price);//поиск по категории и цене которая меньше чем price

    List<Product> findByCategoryAndPriceGreaterThanOrderByPriceAsc(String category, Integer price);
    List<Product> findByCategoryAndPriceLessThanOrderByPriceDesc(String category, Integer price);

    List<Product> findByCategoryAndPriceBetweenOrderByPriceDesc(String category, Integer minPrice, Integer maxPrice);
    List<Product> findByCategoryAndPriceBetweenOrderByPriceAsc(String category, Integer minPrice, Integer maxPrice);
    List<Product> findByCategoryAndPriceBetween(String category, Integer minPrice, Integer maxPrice);
    //List<Product> findByCategoryAndPriceBetween(Category category, double priceAfter, double priceBefore);
}
