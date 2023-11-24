package com.example.api;

import com.example.model.Bill;
import com.example.model.dto.request.BillReqDTO;
import com.example.model.dto.response.BillResDTO;
import com.example.service.bill.IBillService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {

    @Autowired
    private IBillService billService;

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
    public ResponseEntity<?> findById(@PathVariable Long billId) {
        Bill bill = billService.findById(billId).get();
        BillResDTO billResDTOS = bill.toBillResDTO();

        return new ResponseEntity<>(billResDTOS, HttpStatus.OK);
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
}
