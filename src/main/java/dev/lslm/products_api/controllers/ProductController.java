package dev.lslm.products_api.controllers;

import dev.lslm.products_api.models.Product;
import dev.lslm.products_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody Product newProduct) {
        Product savedProduct = productRepository.save(newProduct);
        return  savedProduct;
    }


    @GetMapping
    public List<Product> list() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Product getById(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return product.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }


    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(value = "/{id}")
    public Product update(@RequestBody Product product, @PathVariable Integer id) {
        Optional<Product> productToBeUpdated = productRepository.findById(id);

        if(productToBeUpdated.isPresent()) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Product> productToBeDeleted = productRepository.findById(id);

        if(productToBeDeleted.isPresent()) {
            productRepository.delete(productToBeDeleted.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }
}

