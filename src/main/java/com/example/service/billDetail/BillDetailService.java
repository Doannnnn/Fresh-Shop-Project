package com.example.service.billDetail;

import com.example.model.BillDetail;
import com.example.model.dto.response.BillDetailResDTO;
import com.example.repository.BillDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class BillDetailService implements IBillDetailService{

    @Autowired
    private BillDetailRepository billDetailRepository;
    @Override
    public List<BillDetail> findAll() {
        return null;
    }

    @Override
    public List<BillDetailResDTO> findAllByBillId(Long id) {
        return billDetailRepository.findAllByBillId(id).stream().map(e ->
                new BillDetailResDTO(e.getId(), e.getQuantity(), e.getTotal(), e.getProduct().toProductResDTO())).collect(Collectors.toList());
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
