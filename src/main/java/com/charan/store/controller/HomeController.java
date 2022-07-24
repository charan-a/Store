package com.charan.store.controller;

import com.charan.store.global.GlobalData;
import com.charan.store.service.CategoryService;
import com.charan.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

  @Autowired ProductService productService;
  @Autowired CategoryService categoryService;

  @GetMapping({"/", "/home"})
  public String home(Model model) {
    model.addAttribute("cartCount", GlobalData.cart.size());
    return "index";
  }

  @GetMapping("/shop")
  public String shop(Model model) {
    model.addAttribute("categories", categoryService.getAllCategory());
    model.addAttribute("products", productService.getAllProdcuts());
    model.addAttribute("cartCount", GlobalData.cart.size());
    return "shop";
  }

  @GetMapping("/shop/category/{id}")
  public String shopByCategory(Model model, @PathVariable int id) {
    model.addAttribute("categories", categoryService.getAllCategory());
    model.addAttribute("products", productService.getAllProductsByCategoryId(id));
    model.addAttribute("cartCount", GlobalData.cart.size());
    return "shop";
  }

  @GetMapping("/shop/viewproduct/{id}")
  public String viewProduct(Model model, @PathVariable Long id) {
    model.addAttribute("product", productService.getProductById(id).get());
    model.addAttribute("cartCount", GlobalData.cart.size());
    return "viewProduct";
  }

  @GetMapping("/cart/removeItem/{index}")
  public String cartItemRemove(@PathVariable int index){
    GlobalData.cart.remove(index);
    return "redirect:/cart";
  }



}
