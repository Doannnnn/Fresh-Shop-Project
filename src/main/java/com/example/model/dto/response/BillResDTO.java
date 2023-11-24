package com.example.model.dto.response;

import com.example.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BillResDTO {

    private Long id;

    private BigDecimal total;

    private String shippingMethod;

    private User user;

    private List<BillDetailResDTO> billDetails;
}
