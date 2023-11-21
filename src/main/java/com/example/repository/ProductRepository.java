package com.example.repository;

import com.example.model.Product;
import com.example.model.dto.response.ProductResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    void deleteProductById(@Param("id") Long id);

    List<Product> findAllByDeletedFalse();

}
