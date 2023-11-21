package com.example.service.customer;

import com.example.model.Customer;
import com.example.model.dto.response.CustomerResDTO;
import com.example.service.IGeneralService;

import java.util.LinkedList;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {

    List<CustomerResDTO> findAllCustomerResDTO();
}
