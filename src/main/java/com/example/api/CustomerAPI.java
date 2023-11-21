package com.example.api;

import com.example.exception.DataInputException;
import com.example.model.Customer;
import com.example.model.dto.request.CustomerReqDTO;
import com.example.model.dto.response.CustomerResDTO;
import com.example.service.customer.ICustomerService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> findAllCustomer() {
        List<CustomerResDTO> customerResDTOList = customerService.findAllCustomerResDTO();

        return new ResponseEntity<>(customerResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable Long customerId) {

        Customer customer = customerService.findById(customerId).orElseThrow(() -> new DataInputException("Customer not found"));

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerReqDTO customerReqDTO, BindingResult bindingResult) {
        customerReqDTO.validate(customerReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerReqDTO.toCustomer();
        customerService.save(customer);

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable Long customerId, @RequestBody CustomerReqDTO customerReqDTO, BindingResult bindingResult) {
        customerReqDTO.validate(customerReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerReqDTO.toCustomer();
        customer.setId(customerId);
        customerService.save(customer);

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> delete(@PathVariable Long customerId){
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new DataInputException("Customer not found"));

        customerService.deleteById(customerId);

        return new ResponseEntity<>( HttpStatus.OK);
    }
}
