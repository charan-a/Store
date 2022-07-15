package com.charan.store.service;

import com.charan.store.dto.ProductDto;
import com.charan.store.entity.Product;
import com.charan.store.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {
  @Autowired private ProductRepository productRepository;

  public List<Product> getAllProdcuts() {
    return productRepository.findAll();
  }

  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  public void deleteProductById(Long id) {
    productRepository.deleteById(id);
  }

  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  public List<Product> getAllProductsByCategoryId(Integer id) {
    return productRepository.findAllByCategory_id(id);
  }

  public ProductDto convertProductToDto(Long id) throws Exception {
    Optional<Product> optionalProduct = productRepository.findById(id);
    Product product;
    if (optionalProduct.isPresent()) {
      product = optionalProduct.get();
    } else {
      throw new Exception("Product didnt exist with the given id");
    }
    ProductDto productDto =
        ProductDto.builder()
            .id(product.getId())
            .description(product.getDescription())
            .imageName(product.getImageName())
            .name(product.getName())
            .price(product.getPrice())
            .tag(product.getTag())
            .build();
    return productDto;
  }
}
