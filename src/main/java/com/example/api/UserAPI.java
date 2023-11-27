package com.example.api;

import com.example.model.dto.response.ProductResDTO;
import com.example.model.dto.response.UserResDTO;
import com.example.service.product.IProductService;
import com.example.service.user.IUserService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    private IUserService userService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        List<UserResDTO> userResDTOList = userService.findAllUserResDTO();

        return new ResponseEntity<>(userResDTOList, HttpStatus.OK);
    }
}
