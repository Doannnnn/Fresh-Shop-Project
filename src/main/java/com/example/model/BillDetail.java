package com.example.model;

import com.example.model.dto.response.BillDetailResDTO;
import com.example.model.dto.response.BillResDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bill_details")
@Accessors(chain = true)
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public BillDetailResDTO toBillDetailResDTO(){
        return new BillDetailResDTO()
                .setId(id)
                .setQuantity(quantity)
                .setTotal(total)
                .setProduct(product.toProductResDTO());
    }
}
