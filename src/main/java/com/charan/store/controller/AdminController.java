package com.charan.store.controller;

import com.charan.store.entity.Category;
import com.charan.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

  @Autowired CategoryService categoryService;

  @GetMapping("/admin")
  public String adminHome() {
    return "adminHome";
  }

  @GetMapping("/admin/categories")
  public String getCategories() {
    return "categories";
  }

  @GetMapping("/admin/categories/add")
  public String getCategoriesAdd(Model model) {
    model.addAttribute("category", new Category());
    return "categoriesAdd";
  }

  @PostMapping("/admin/categories/add")
  public String postCategoriesAdd(@ModelAttribute("category") Category category) {
    categoryService.saveCategory(category);
    return "redirect:/admin/categories";
  }


}
