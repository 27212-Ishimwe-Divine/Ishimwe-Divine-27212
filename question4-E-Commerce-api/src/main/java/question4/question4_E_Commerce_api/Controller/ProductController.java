package question4.question4_E_Commerce_api.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import question4.question4_E_Commerce_api.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    public ProductController() {
        products.add(new Product(nextId++, "iPhone 17", "Latest Apple smartphone", 999.99, "Electronics", 50, "Apple"));
        products.add(new Product(nextId++, "Samsung Galaxy S23", "Flagship Android phone", 899.99, "Electronics", 30,
                "Samsung"));
        products.add(
                new Product(nextId++, "MacBook Pro", "High-performance laptop", 2499.99, "Electronics", 20, "Apple"));
        products.add(
                new Product(nextId++, "Dell XPS 15", "Premium Windows laptop", 1799.99, "Electronics", 15, "Dell"));
        products.add(
                new Product(nextId++, "Sony WH-1000XM5", "Noise-cancelling headphones", 399.99, "Audio", 100, "Sony"));
        products.add(new Product(nextId++, "Nike Air Max", "Running shoes", 129.99, "Footwear", 200, "Nike"));
        products.add(new Product(nextId++, "Adidas Ultraboost", "Comfortable running shoes", 179.99, "Footwear", 150,
                "Adidas"));
        products.add(new Product(nextId++, "Levi's 501 Jeans", "Classic denim jeans", 69.99, "Clothing", 0, "Levi's"));
        products.add(new Product(nextId++, "Canon EOS R5", "Professional camera", 3899.99, "Electronics", 10, "Canon"));
        products.add(new Product(nextId++, "Kindle Paperwhite", "E-reader with backlight", 139.99, "Electronics", 80,
                "Amazon"));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        if (page != null && limit != null) {
            int start = page * limit;
            int end = Math.min(start + limit, products.size());
            List<Product> sub = start < products.size() ? products.subList(start, end) : new ArrayList<>();
            return ResponseEntity.ok(sub);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return products.stream().filter(p -> p.getProductId().equals(productId)).findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return products.stream().filter(p -> p.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-stock")
    public List<Product> getInStockProducts() {
        return products.stream().filter(p -> p.getStockQuantity() > 0).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setProductId(nextId++);
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                products.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        Product product = products.stream().filter(p -> p.getProductId().equals(productId)).findFirst().orElse(null);
        if (product != null) {
            product.setStockQuantity(quantity);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));
        if (removed)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
