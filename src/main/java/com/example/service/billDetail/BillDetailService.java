package com.example.service.billDetail;

import com.example.model.BillDetail;

import java.util.List;
import java.util.Optional;

public class BillDetailService implements IBillDetailService{
    @Override
    public List<BillDetail> findAll() {
        return null;
    }

    @Override
    public Optional<BillDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(BillDetail billDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
