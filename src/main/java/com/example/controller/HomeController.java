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
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showProductPage() {

        return new ModelAndView("views/shop");
    }

    @GetMapping("/product-detail")
    public ModelAndView showProductDetailPage() {


        return new ModelAndView("views/product-detail");
    }

    @GetMapping("/about")
    public ModelAndView showAboutPage() {


        return new ModelAndView("views/about");
    }

    @GetMapping("/gallery")
    public ModelAndView showGalleryPage() {


        return new ModelAndView("views/gallery");
    }

    @GetMapping("/contact")
    public ModelAndView showContactPage() {


        return new ModelAndView("views/contact");
    }

    @GetMapping("/cart")
    public ModelAndView showCartPage() {


        return new ModelAndView("views/cart");
    }

    @GetMapping("/checkout")
    public ModelAndView showCheckoutPage() {


        return new ModelAndView("views/checkout");
    }

}
