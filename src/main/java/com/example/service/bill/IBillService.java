package com.example.service.bill;

import com.example.model.Bill;
import com.example.model.Enum.EStatus;
import com.example.model.dto.request.BillReqDTO;
import com.example.model.dto.response.BillResDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface IBillService extends IGeneralService<Bill, Long> {

    void updateStatus(Long id, EStatus newStatus);

    List<BillResDTO> findAllBillResDTO();

    List<BillResDTO> findAllBillByUser(String username);

    Bill create(BillReqDTO billReqDTO);
}
