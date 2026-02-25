package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    public List<Product> findAll();
    public Product findById(String id);
    public Product update(Product product);
    public void deleteProductById(String id);
}