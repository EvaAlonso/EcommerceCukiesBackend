package com.factoriaF5.cukies.controller;

import com.factoriaF5.cukies.DTOs.product.ProductDTO;
import com.factoriaF5.cukies.model.Product;
import com.factoriaF5.cukies.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(){
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDTO>> getProductById(@PathVariable int id){
        Optional<ProductDTO> productDTO = productService.findProductById(id);
        if (productDTO.isPresent()){
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }
        //Aquí no tendría que devolver una exception?
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        //Hay que buscar si existen categorías y añadir listas categorías
        ProductDTO newProductDTO = productService.createProduct(productDTO);
        return new ResponseEntity<>(newProductDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO updateProductDTO){
        try {
            ProductDTO updatedProductDTO = productService.updatedProduct(id, updateProductDTO);
            return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Optional<ProductDTO> productOptionalDTO = productService.findProductById(id);
        if (productOptionalDTO.isPresent()){
            Product product = productOptionalDTO.get();
            productService.deleteProduct(id);
            String message = "Product " + product.getName() + " has been deleted.";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

}
