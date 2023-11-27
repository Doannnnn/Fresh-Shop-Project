package com.example.repository;

import com.example.model.BillDetail;
import com.example.model.dto.response.BillDetailResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    List<BillDetail> findAllByBillId(Long billId);
}
