package com.charan.store.service;

import com.charan.store.entity.Category;
import com.charan.store.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

  @Autowired CategoryRepository categoryRepository;

  public List<Category> getAllCategory() {
    return categoryRepository.findAll();
  }

  //  private Category getCategory() {
  //    return categoryRepository.getReferenceById();
  //  }

  public void saveCategory(Category category) {
    categoryRepository.save(category);
  }
}
