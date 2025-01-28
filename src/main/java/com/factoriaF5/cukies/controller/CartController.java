package com.factoriaF5.cukies.controller;

import com.factoriaF5.cukies.DTOs.cart.CartDTOResponse;
import com.factoriaF5.cukies.model.Customer;
import com.factoriaF5.cukies.service.CartService;
import com.factoriaF5.cukies.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CustomerService customerService;

    public CartController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartDTOResponse> getCart(@PathVariable int customerId) {
        CartDTOResponse cartDTO = cartService.getCartByCustomer(customerService.findById(customerId));
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
