package com.example.model;

import com.example.model.Enum.ERole;
import com.example.model.dto.response.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Accessors(chain = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private ERole role;

    private Boolean deleted = false;

    public CustomerResDTO toCustomerResDTO(){
        return new CustomerResDTO()
                .setId(id)
                .setName(name)
                .setPhone(phone)
                .setEmail(email)
                .setAddress(address);
    }

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private List<Bill> bills;
}
