package com.practice.design.factory.config;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public interface PayClientConfig {

    /**
     * 配置验证参数是
     *
     * @param validator 校验对象
     * @return 配置好的验证参数
     */
    Set<ConstraintViolation<PayClientConfig>> verifyParam(Validator validator);

    // TODO @aquan：貌似抽象一个 validation group 就好了！
    /**
     * 参数校验
     *
     * @param validator 校验对象
     */
    default void validate(Validator validator) {
        Set<ConstraintViolation<PayClientConfig>> violations = verifyParam(validator);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}