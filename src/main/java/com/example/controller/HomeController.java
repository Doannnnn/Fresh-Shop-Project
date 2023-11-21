package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/home")
    public ModelAndView showPage() {

        return new ModelAndView("views/home");
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
