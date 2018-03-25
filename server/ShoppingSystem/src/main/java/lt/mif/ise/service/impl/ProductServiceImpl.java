package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Product;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getById(String id) {
        // wtf is this?????
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product modify(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);
    }
}
