package com.example.service.bill;

import com.example.model.*;
import com.example.model.dto.request.BillReqDTO;
import com.example.model.dto.response.BillResDTO;
import com.example.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillService implements IBillService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public List<BillResDTO> findAllBillResDTO() {
        return billRepository.findAll().stream().map(e ->
                new BillResDTO(e.getId(), e.getTotal(), e.getShippingMethod(), e.getUser(), e.getBillDetails().stream().map(BillDetail::toBillDetailResDTO).collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }

    @Override
    public List<BillResDTO> findAllBillByUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<Bill> bills = billRepository.findAllByUser_Id(user.getId());

            return bills.stream().map(e ->
                    new BillResDTO(e.getId(), e.getTotal(), e.getShippingMethod(), e.getUser(), e.getBillDetails().stream().map(BillDetail::toBillDetailResDTO).collect(Collectors.toList()))
            ).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Bill create(BillReqDTO billReqDTO) {
        User user = userRepository.findByUsername(billReqDTO.getUsername());
        Cart cart = cartRepository.findCartByUser_Id(user.getId());
        List<CartDetail> cartDetails = cartDetailRepository.findByCart_Id(cart.getId());
        BigDecimal total = new BigDecimal(billReqDTO.getTotal());
        String shippingMethod = billReqDTO.getShippingMethod();

        Bill bill = new Bill();
        bill.setTotal(total);
        bill.setShippingMethod(shippingMethod);
        bill.setUser(user);
        billRepository.save(bill);

        for (CartDetail cartDetail : cartDetails) {
            BillDetail billDetail = new BillDetail();
            billDetail.setQuantity(cartDetail.getQuantity());
            billDetail.setTotal(cartDetail.getTotalAmount());
            billDetail.setBill(bill);
            billDetail.setProduct(cartDetail.getProduct());

            billDetailRepository.save(billDetail);

            bill.getBillDetails().add(billDetail);
        }

        cartDetailRepository.deleteAllByCart_Id(cart.getId());

        return bill;
    }

    @Override
    public void save(Bill bill) {

    }

    @Override
    public void deleteById(Long id) {

    }

}
