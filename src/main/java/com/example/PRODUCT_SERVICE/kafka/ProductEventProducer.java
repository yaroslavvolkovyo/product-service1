package com.example.PRODUCT_SERVICE.kafka;

import com.example.PRODUCT_SERVICE.entity.Product;
import com.example.PRODUCT_SERVICE.eventDto.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void sendProductCreatedEvent(Product product) {
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .productArticle(product.getProductArticle())
                .quantity(product.getQuantity())
                .build();
        kafkaTemplate.send("product.created", event);
    }
}
