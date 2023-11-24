package com.example.model.dto.response;

import com.example.model.Enum.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserResDTO {
    private Long id;
    private String username;
    private String email;
    private String address;
    private String fullName;
    private String phoneNumber;
    private ERole role;

    public UserResDTO(Long id, String username, String email, String address, String fullName, String phoneNumber, ERole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
