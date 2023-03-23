package com.sda.onlineshopjavaremotero46.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String name;
    private String description;
    private String price;
    private String category;
    private String unitsInStock;
}
