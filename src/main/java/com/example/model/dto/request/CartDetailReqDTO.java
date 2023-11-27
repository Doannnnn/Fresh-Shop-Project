package com.example.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailReqDTO implements Validator {

    private String quantity;

    private String productId;

    private String username;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartDetailReqDTO cartDetailReqDTO = (CartDetailReqDTO) o;
        String quantityStr = cartDetailReqDTO.getQuantity();

        if (quantityStr == null || quantityStr.isEmpty()) {
            errors.rejectValue("quantity", "quantity.null", "Nhập số lượng.");
            return;
        }

        try {
            Long quantity = Long.parseLong(quantityStr);

            if (quantity <= 0) {
                errors.rejectValue("quantity", "quantity.zero", "Số lượng phải lớn hơn 0.");
                return;
            }
            if (quantity >= 20) {
                errors.rejectValue("quantity", "quantity.max", "Số lượng phải nhỏ hơn hoặc bằng 20.");
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("quantity", "quantity.invalid", "Số lượng không hợp lệ.");
        }
    }
}
