package com.example.repository;

import com.example.model.Bill;
import com.example.model.Enum.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Modifying
    @Query("UPDATE Bill b SET b.status = :newStatus WHERE b.id = :billId")
    void updateStatus(@Param("billId") Long billId, @Param("newStatus") EStatus newStatus);


    List<Bill> findAllByUser_Id(Long user_id);
}
