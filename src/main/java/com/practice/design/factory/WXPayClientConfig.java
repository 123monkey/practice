package com.practice.design.factory;

import com.practice.design.factory.config.PayClientConfig;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Data
public class WXPayClientConfig  implements PayClientConfig {
    @Override
    public Set<ConstraintViolation<PayClientConfig>> verifyParam(Validator validator) {
        return null;
    }

    @Override
    public void validate(Validator validator) {

    }
}
