package com.example.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;


@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/product"; // Điều hướng đến trang admin nếu có vai trò ADMIN
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            return "redirect:/home"; // Điều hướng đến trang user nếu có vai trò Client
        } else {
            throw new IllegalStateException("Vai trò không hợp lệ.");
        }
    }

    @GetMapping("/home")
    public String showPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/home";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);

        // Kiểm tra vai trò và thêm vào model nếu cần
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }
        return "views/home";
    }

    @GetMapping("/product")
    public String showAdminPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("isAdmin",true);
        return "admin/views/product";
    }


    @GetMapping("/shop")
    public String showProductPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/shop";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);

        // Kiểm tra vai trò và thêm vào model nếu cần
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }
        return "views/shop";
    }


    @GetMapping("/about")
    public String showAboutPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/about";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);

        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }
        return "views/about";

    }

    @GetMapping("/gallery")
    public String showGalleryPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/gallery";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);


        // Kiểm tra vai trò và thêm vào model nếu cần
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }
        return "views/gallery";

    }

    @GetMapping("/contact")
    public String showContactPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/contact";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);

        // Kiểm tra vai trò và thêm vào model nếu cần
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }
        return "views/contact";
    }

    @GetMapping("/cart")
    public String showCartPage(Model model, Authentication authentication) {
        if(authentication == null){
            return "views/cart";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);

        // Kiểm tra vai trò và thêm vào model nếu cần
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isClient",true);
        }

        return "views/cart";
    }

}
