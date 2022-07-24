package com.charan.store.repository;

import com.charan.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByCategory_id(Integer id);
}
