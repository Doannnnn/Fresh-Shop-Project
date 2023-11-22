package com.example.controller;

import com.example.service.auth.AuthService;
import com.example.service.auth.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    @GetMapping("/login")
    public String showLogin(){
        return "/auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        RegisterRequest client = new RegisterRequest();
        model.addAttribute("client", client);

        return "/auth/register";
    }


    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("client") RegisterRequest request,
                               BindingResult result,
                               Model model){
        authService.checkUsernameOrPhoneNumberOrEmail(request, result);
        model.addAttribute("client",request);
        if(result.hasErrors()){
            return "/auth/register";
        }
        authService.register(request);
        return "redirect:/register?success";
    }
}
