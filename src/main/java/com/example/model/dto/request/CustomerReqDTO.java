package com.example.model.dto.request;

import com.example.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerReqDTO implements Validator {

    private String id;

    private String name;

    private String email;

    private String phone;

    private String address;

    public Customer toCustomer(){
        return new Customer()
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
