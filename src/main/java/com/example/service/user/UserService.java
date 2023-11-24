package com.example.service.user;

import com.example.model.User;
import com.example.model.dto.response.ProductResDTO;
import com.example.model.dto.response.UserResDTO;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResDTO> findAllUserResDTO() {
        return userRepository.findAll().stream().map(u ->
                new UserResDTO(u.getId(), u.getUsername(), u.getEmail(), u.getAddress(),u.getFullName(), u.getPhoneNumber(),  u.getRole())
        ).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
