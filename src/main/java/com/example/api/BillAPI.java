package com.example.api;

import com.example.model.Bill;
import com.example.model.CartDetail;
import com.example.model.Enum.EStatus;
import com.example.model.dto.request.BillReqDTO;
import com.example.model.dto.response.BillDetailResDTO;
import com.example.model.dto.response.BillResDTO;
import com.example.service.bill.IBillService;
import com.example.service.billDetail.IBillDetailService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {

    @Autowired
    private IBillService billService;

    @Autowired
    private IBillDetailService billDetailService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping()
    public ResponseEntity<?> findAllBill() {
        List<BillResDTO> billResDTOS = billService.findAllBillResDTO();

        return new ResponseEntity<>(billResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findAllBillByUser(@PathVariable String username) {
        List<BillResDTO> billResDTOS = billService.findAllBillByUser(username);

        return new ResponseEntity<>(billResDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{billId}")
    public ResponseEntity<?> findAllBillDetailByBillId(@PathVariable Long billId) {
        List<BillDetailResDTO> billDetailResDTOS = billDetailService.findAllByBillId(billId);

        return new ResponseEntity<>(billDetailResDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BillReqDTO billReqDTO, BindingResult bindingResult) {
        billReqDTO.validate(billReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Bill bill = billService.create(billReqDTO);

        BillResDTO billResDTO = bill.toBillResDTO();

        return new ResponseEntity<>(billResDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{billId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long billId, @RequestBody String newStatus) {

        Optional<Bill> existingBill = billService.findById(billId);

        if (existingBill.isPresent()) {
            billService.updateStatus(billId, EStatus.valueOf(newStatus));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bill not found", HttpStatus.NOT_FOUND);
        }
    }
}
