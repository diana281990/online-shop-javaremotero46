package com.sda.onlineshopjavaremotero46.controller;


import com.sda.onlineshopjavaremotero46.dto.*;
import com.sda.onlineshopjavaremotero46.service.CartService;
import com.sda.onlineshopjavaremotero46.service.OrderService;
import com.sda.onlineshopjavaremotero46.service.ProductService;
import com.sda.onlineshopjavaremotero46.service.UserAccountService;
import com.sda.onlineshopjavaremotero46.validator.UserAccountValidator;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountValidator userAccountValidator;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/addProduct")
    public String addProductGet(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto, @RequestParam("productImg") MultipartFile multipartFile) {
        System.out.println(productDto);
        log.info("apelat add product");
        productService.create(productDto, multipartFile);
        return "redirect:/addProduct";
    }

    @GetMapping("/home")
    public String homeGet(Model model) {
        List<ProductDto> productDtoList = productService.getAllProductDtoList();
        model.addAttribute("productDtoList", productDtoList);
        System.out.println(productDtoList);
        return "home";
    }

    @GetMapping("/product/{id}")
    public String viewProductGet(Model model, @PathVariable(value = "id") String id) {
        System.out.println("click pe produsul cu id-ul" + id);
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(id);
        if (optionalProductDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("productDto", optionalProductDto.get());
        ProductQuantityDto productQuantityDto = new ProductQuantityDto();
        model.addAttribute("productQuantityDto", productQuantityDto);
        return "viewProduct";
    }

    @PostMapping("/product/{id}")
    public String addToCartPost(@ModelAttribute ProductQuantityDto productQuantityDto,
                                @PathVariable(value = "id") String id, Authentication authentication) {
        System.out.println(productQuantityDto);
        System.out.println("adaug in cos produsul cu id: " + id);
        System.out.println(authentication.getName());
        cartService.addToCart(id, productQuantityDto, authentication.getName());
        return "redirect:/product/" + id;
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        UserAccountDto userAccountDto = new UserAccountDto();
        model.addAttribute("userAccountDto", userAccountDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute UserAccountDto userAccountDto, BindingResult bindingResult) {
        System.out.println(userAccountDto);
        userAccountValidator.validate(userAccountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userAccountService.userRegister(userAccountDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @GetMapping("/checkout")
    public String checkoutGet(Model model, Authentication authentication) {

        CheckoutDto checkoutDto = cartService.getCheckoutDtoByUserEmail(authentication.getName());
        model.addAttribute("checkoutDto", checkoutDto);
        return "checkout";
    }

    @GetMapping("/confirmation")
    public String confirmationGet(){

        return "error";
    }
    @PostMapping("/confirmation")
    public  String confirmationPost(Model model,Authentication authentication){
        orderService.placeOrder(authentication.getName());
        CheckoutDto checkoutDto = cartService.getCheckoutDtoByUserEmail(authentication.getName());
        model.addAttribute("checkoutDto", checkoutDto);
        return "confirmation";
    }
}






