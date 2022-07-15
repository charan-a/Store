package com.charan.store.controller;

import com.charan.store.dto.ProductDto;
import com.charan.store.entity.Category;
import com.charan.store.entity.Product;
import com.charan.store.service.CategoryService;
import com.charan.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class AdminController {

  @Autowired CategoryService categoryService;

  @Autowired ProductService productService;

  public static String uploadDir =
      System.getProperty("user.dir") + "/src/main/resources/static/productImages";

  @GetMapping("/admin")
  public String adminHome() {
    return "adminHome";
  }

  @GetMapping("/admin/categories")
  public String getCategories(Model model) {
    List<Category> allCategories = categoryService.getAllCategory();
    model.addAttribute("categories", allCategories);
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

  @GetMapping("/admin/categories/delete/{id}")
  public String delCategory(@PathVariable int id) {
    categoryService.delCategoryById(id);
    return "redirect:/admin/categories";
  }

  @GetMapping("/admin/categories/update/{id}")
  public String updateCategory(@PathVariable int id, Model model) {
    Optional<Category> optCategory = categoryService.getCategoryById(id);
    if (optCategory.isPresent()) {
      model.addAttribute("category", optCategory.get());
      return "categoriesAdd";
    } else {
      return "404";
    }
  }

  // products controllers

  @GetMapping("/admin/products")
  public String getProducts(Model model) {
    List<Product> allProdcuts = productService.getAllProdcuts();
    model.addAttribute("products", allProdcuts);
    return "products";
  }

  @GetMapping("/admin/products/add")
  public String addProduct(Model model) {
    model.addAttribute("productDTO",new ProductDto());
    model.addAttribute("categories", categoryService.getAllCategory());
    return "productsAdd";
  }

  @PostMapping("/admin/products/add")
  public String postProduct(
      @ModelAttribute("productDTO") ProductDto productDto,
      @RequestParam("productImage") MultipartFile multipartFile,
      @RequestParam("imgName") String imgName)
      throws IOException {

    Product product = new Product();
    product.setId(productDto.getId());
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setTag(productDto.getTag());
    product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()).get());
    product.setDescription(productDto.getDescription());

    try {
      String imgUUID;
      if (!multipartFile.isEmpty()) {
        imgUUID = multipartFile.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDir, imgUUID);
        Files.write(fileNameAndPath, multipartFile.getBytes());
      } else {
        imgUUID = imgName;
      }
      product.setImageName(imgUUID);
      productService.saveProduct(product);
    } catch (Exception exp) {
      log.error("[postProduct] error while saving post req : ", exp);
    }
    return "redirect:/admin/products";
  }

  @GetMapping("/admin/product/update/{id}")
  public String updateProduct(@PathVariable Long id, Model model) throws Exception {
    ProductDto productDto = productService.convertProductToDto(id);
    model.addAttribute("productDTO", productDto);
    model.addAttribute("categories", categoryService.getAllCategory());
    return "productsAdd";
  }

  @GetMapping("/admin/product/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProductById(id);
    return "redirect:/admin/products";
  }
}
