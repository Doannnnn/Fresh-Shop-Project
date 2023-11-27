package com.example.service.billDetail;

import com.example.model.BillDetail;
import com.example.model.dto.response.BillDetailResDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface IBillDetailService extends IGeneralService<BillDetail, Long> {

    List<BillDetailResDTO> findAllByBillId(Long id);
}
