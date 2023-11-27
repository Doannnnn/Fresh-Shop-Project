package com.example.model;

import com.example.model.Enum.EStatus;
import com.example.model.dto.response.BillResDTO;
import com.example.model.dto.response.ProductResDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
@Accessors(chain = true)
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total;

    private String shippingMethod;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "bill")
    private List<BillDetail> billDetails = new ArrayList<>();

    public BillResDTO toBillResDTO(){
        return new BillResDTO()
                .setId(id)
                .setTotal(total)
                .setBillDetails(billDetails.stream().map(BillDetail::toBillDetailResDTO).collect(Collectors.toList()));
    }
}
