package com.example.service.user;

import com.example.model.User;
import com.example.model.dto.response.ProductResDTO;
import com.example.model.dto.response.UserResDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface IUserService extends IGeneralService<User, Long> {
    List<UserResDTO> findAllUserResDTO();
}
