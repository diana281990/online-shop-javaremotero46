package com.sda.onlineshopjavaremotero46.dto;

import lombok.*;
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
    @ToString.Exclude
    private String img;
}
