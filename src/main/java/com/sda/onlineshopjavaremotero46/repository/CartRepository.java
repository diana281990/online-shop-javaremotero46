package com.sda.onlineshopjavaremotero46.repository;

import com.sda.onlineshopjavaremotero46.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserAccountEmail(String email);
}
