package com.example.service.product;

import com.example.model.Image;
import com.example.model.Product;
import com.example.model.dto.response.ProductResDTO;
import com.example.repository.ImageRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductResDTO> findAllProductResDTO() {
        return productRepository.findAllByDeletedFalse().stream().map(e ->
                new ProductResDTO(e.getId(), e.getName(), e.getPrice(), e.getDescription(), e.getCategory(), e.getExpirationDate(), e.getImages())
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public void save(Product product) {
        productRepository.save(product);

        List<Image> images = product.getImages();

        if (images != null && !images.isEmpty()) {
            for (Image image : images) {
                image.setProduct(product);
                imageRepository.save(image);
            }
        }
    }

    @Override
    public void update(Product product) {
        imageRepository.deleteAllByProduct_Id(product.getId());

        productRepository.save(product);

        List<Image> images = product.getImages();

        if (images != null && !images.isEmpty()) {
            for (Image image : images) {
                image.setProduct(product);
                imageRepository.save(image);
            }
        }

    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteProductById(id);
    }

}
