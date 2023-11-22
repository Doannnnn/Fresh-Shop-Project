package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ProductController {

//    @GetMapping("/product")
//    public ModelAndView showProductPage() {
//
//        return new ModelAndView("/admin/views/product");
//    }

    @GetMapping("/customer")
    public ModelAndView showCustomerPage() {

        return new ModelAndView("/admin/views/customer");
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboardPage() {

        return new ModelAndView("/admin/views/dashboard");
    }

    @GetMapping("/bill")
    public ModelAndView showBillPage() {

        return new ModelAndView("/admin/views/bill");
    }
}
