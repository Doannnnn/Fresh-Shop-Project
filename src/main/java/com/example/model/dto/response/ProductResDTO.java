package com.example.model.dto.response;

import com.example.model.Enum.ECategory;
import com.example.model.Image;
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
@Getter
@Setter
@Accessors(chain = true)
public class ProductResDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private ECategory category;

    private LocalDate expirationDate;

    private List<ImageResDTO> images;

    public ProductResDTO (Long id, String name, BigDecimal price, String description, ECategory category, LocalDate expirationDate, List<Image> images){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.expirationDate = expirationDate;
        this.images = images.stream().map(Image::toImageResDTO).collect(Collectors.toList());
    }

}
