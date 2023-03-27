package com.sda.onlineshopjavaremotero46.controller;

import com.sda.onlineshopjavaremotero46.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sda.onlineshopjavaremotero46.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private ProductService productService;

    @GetMapping("/addProduct")
    public String addProductGet(Model model) {

        ProductDto productDto = new ProductDto();

        model.addAttribute("productDto", productDto);

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto,@RequestParam("productImg") MultipartFile multipartFile) {
        System.out.println(productDto);
        log.info("Diana was here");
        productService.create(productDto,multipartFile);

        return "redirect:/addProduct";

    }

    @GetMapping("/home")
    public String homeGet(Model model) {

        List<ProductDto> productDtoList = productService.getAllProductDtoList();
        model.addAttribute("productDtoList", productDtoList);
        System.out.println(productDtoList);

        return "home";
    }

    @GetMapping("/product/{productId}")
    public String viewProductGet(Model model, @PathVariable(value = "productId") String id) {
        System.out.println("am dat click pe produsul cu id-ul:" + id);
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(id);
        if (optionalProductDto.isEmpty()) {
            return "error";

        }
        model.addAttribute("productDto", optionalProductDto.get());
        return "viewProduct";
    }
    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }
    @GetMapping("/login")
    public String loginSet(){
        return "login";
    }
}
