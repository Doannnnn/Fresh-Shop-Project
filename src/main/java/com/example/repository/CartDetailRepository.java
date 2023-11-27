package com.example.repository;

import com.example.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    @Modifying
    @Query("UPDATE CartDetail cd SET cd.quantity = :newQuantity WHERE cd.id = :cartDetailId")
    void changeQuantity(@Param("cartDetailId") Long cartDetailId, @Param("newQuantity") Long newQuantity);

    void deleteAllByCart_Id(Long cart_id);

    CartDetail findByCart_IdAndProduct_Id(Long cartId, Long productId);

    List<CartDetail> findByCart_Id(Long cartId);
}
