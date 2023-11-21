package com.example.repository;

import com.example.model.Customer;
import com.example.model.dto.response.CustomerResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT NEW com.example.model.dto.response.CustomerResDTO ( " +
            "cus.id, " +
            "cus.name, " +
            "cus.email, " +
            "cus.phone, " +
            "cus.address" +
            ") " +
            "FROM Customer AS cus WHERE cus.deleted = false "
    )
    List<CustomerResDTO> findAllCustomerResDTO();

    @Modifying
    @Query("UPDATE Customer c SET c.deleted = true WHERE c.id = :customerId")
    void deleteCustomer(@Param("customerId") Long customerId);

}
