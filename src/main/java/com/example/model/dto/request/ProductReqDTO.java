package com.example.model.dto.request;

import com.example.model.Enum.ECategory;
import com.example.model.Image;
import com.example.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductReqDTO implements Validator {

    private String id;

    private String name;

    private String price;

    private String description;

    private String category;

    private String expirationDate;

    private List<ImageReqDTO> images;

    public Product toProduct() {
        return new Product()
                .setName(name)
                .setPrice(new BigDecimal(price))
                .setDescription(description)
                .setCategory(ECategory.valueOf(category))
                .setExpirationDate(LocalDate.parse(expirationDate))
                .setImages(images.stream().map(ImageReqDTO::toImage).collect(Collectors.toList()));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductReqDTO productReqDTO = (ProductReqDTO) o;

        if (StringUtils.isEmpty(productReqDTO.getName())) {
            errors.rejectValue("name", "name.null", "Không được để trống.");
        }
        if (StringUtils.isEmpty(productReqDTO.getPrice())) {
            errors.rejectValue("price", "price.null", "Không được để trống.");
        }
        if (StringUtils.isEmpty(productReqDTO.getDescription())) {
            errors.rejectValue("description", "description.null", "Không được để trống.");
        }
        if (StringUtils.isEmpty(productReqDTO.getExpirationDate())) {
            errors.rejectValue("expirationDate", "expirationDate.null", "Không được để trống.");
        }
        if (productReqDTO.getImages() == null || productReqDTO.getImages().isEmpty()) {
            errors.rejectValue("images", "images.null", "Không được để trống.");
        }
    }
}
