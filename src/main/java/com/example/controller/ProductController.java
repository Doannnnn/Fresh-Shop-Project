package com.example.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ProductController {

//    @GetMapping("/product")
//    public ModelAndView showProductPage() {
//
//        return new ModelAndView("/admin/views/product");
//    }


    @GetMapping("/user")
    public String showCustomerPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("isAdmin",true);
        return "/admin/views/  user";
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("isAdmin",true);
        return "/admin/views/dashboard";
    }
    @GetMapping("/bill")
    public String showBillPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("isAdmin",true);
        return "/admin/views/bill";
    }
}
