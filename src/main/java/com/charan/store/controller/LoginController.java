package com.charan.store.controller;

import com.charan.store.global.GlobalData;
import com.charan.store.model.Role;
import com.charan.store.model.User;
import com.charan.store.repository.RoleRepository;
import com.charan.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;

  @GetMapping("/login")
  public String login() {
    GlobalData.cart.clear();
    return "login";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping("/register")
  public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request)
      throws ServletException {
    String password = user.getPassword();
    user.setPassword(bCryptPasswordEncoder.encode(password));
    List<Role> roles = new ArrayList<>();
    roles.add(roleRepository.findById(2).get());
    user.setRoles(roles);
    userRepository.save(user);
    request.login(user.getEmail(), password);
    return "redirect:/";
  }
}
