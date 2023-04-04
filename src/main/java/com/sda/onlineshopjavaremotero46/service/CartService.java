package com.sda.onlineshopjavaremotero46.service;

import com.sda.onlineshopjavaremotero46.dto.CartEntryDto;
import com.sda.onlineshopjavaremotero46.dto.CheckoutDto;
import com.sda.onlineshopjavaremotero46.dto.ProductQuantityDto;
import com.sda.onlineshopjavaremotero46.entities.Cart;
import com.sda.onlineshopjavaremotero46.entities.CartEntry;
import com.sda.onlineshopjavaremotero46.entities.Product;
import com.sda.onlineshopjavaremotero46.mapper.CartEntryMapper;
import com.sda.onlineshopjavaremotero46.repository.CartEntryRepository;
import com.sda.onlineshopjavaremotero46.repository.CartRepository;
import com.sda.onlineshopjavaremotero46.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartEntryMapper cartEntryMapper;
    private CartEntryRepository cartEntryRepository;

    public void addToCart(String productId, ProductQuantityDto productQuantityDto, String loggedInUserEmail) {
        CartEntry cartEntry = new CartEntry();

        Cart cart = cartRepository.findByUserAccountEmail(loggedInUserEmail);
        cartEntry.setCart(cart);

        Optional<Product> optionalProduct = productRepository.findById(Long.valueOf(productId));
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product id is not valid");
        }
        Product product = optionalProduct.get();
        cartEntry.setProduct(product);

        cartEntry.setQuantity(Integer.valueOf(productQuantityDto.getQuantity()));

        cartEntryRepository.save(cartEntry);

    }

    public CheckoutDto getCheckoutDtoByUserEmail(String userEmail) {

        Cart cart = cartRepository.findByUserAccountEmail(userEmail);

        List<CartEntryDto> cartEntryDtoList = new ArrayList<>();
        Integer subtotal = 0;
        for (CartEntry cartEntry : cart.getCartEntryList()) {
            CartEntryDto cartEntryDto = cartEntryMapper.map(cartEntry);
            cartEntryDtoList.add(cartEntryDto);
            subtotal = subtotal + cartEntry.getProduct().getPrice()*cartEntry.getQuantity();


        }
        return CheckoutDto.builder()
                .cartEntryDtoList(cartEntryDtoList)
                .subtotal(String.valueOf(subtotal))
                .shippingFee("50")
                .total(String.valueOf(subtotal + 50))
                .build();
    }
}
