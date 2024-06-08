package exercise.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;

    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false, name = "min") Integer min,
                                     @RequestParam(required = false, name = "max") Integer max) {

        var sort = Sort.by(Sort.Direction.ASC, "price");

        if (min != null && max != null) {
            return productRepository.findAllByPriceBetween(min, max, sort);
        } else if (min != null) {
            return productRepository.findAllByPriceGreaterThan(min, sort);
        } else if (max != null) {
            return productRepository.findAllByPriceLessThan(max, sort);
        }

        return productRepository.findAll(sort);
    }

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
