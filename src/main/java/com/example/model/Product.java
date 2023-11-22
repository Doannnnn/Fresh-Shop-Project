package com.example.model;

import com.example.model.Enum.ECategory;
import com.example.model.dto.response.ProductResDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @Column(nullable = false, updatable = false)
    private Boolean deleted = false;

    public ProductResDTO toProductResDTO(){
        return new ProductResDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setDescription(description)
                .setCategory(category)
                .setExpirationDate(expirationDate)
                .setImages(images.stream().map(Image::toImageResDTO).collect(Collectors.toList()));
    }
}
