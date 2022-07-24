package com.charan.store.service;

import com.charan.store.entity.Category;
import com.charan.store.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

  @Autowired CategoryRepository categoryRepository;

  public List<Category> getAllCategory() {
    return categoryRepository.findAll();
  }

  public void saveCategory(Category category) {
    categoryRepository.save(category);
  }

  public void delCategoryById(int id) {
    categoryRepository.deleteById(id);
  }

  public Optional<Category> getCategoryById(int id) {
    return categoryRepository.findById(id);
  }
}
