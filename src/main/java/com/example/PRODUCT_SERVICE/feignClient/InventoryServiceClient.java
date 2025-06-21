package com.example.PRODUCT_SERVICE.feignClient;

import com.example.PRODUCT_SERVICE.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryServiceClient {
    @PostMapping("/v1/inventory")
    ResponseEntity<Void> saveInventory(@RequestBody InventoryDto inventoryDto);

    @GetMapping("/v1/inventory/{article}")
    int getQuantity(@PathVariable("article") Long article);
}
