package com.practice.design.factory;

import com.practice.design.factory.entity.CommonResult;
import com.practice.design.factory.entity.PayOrderSubmitReqVO;
import com.practice.design.factory.entity.PayOrderUnifiedRespDTO;
import com.practice.design.factory.service.PayOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.practice.design.factory.entity.CommonResult.success;

/**
 *  提交订单
 */
public class PayOrderController {

    private final PayOrderService payOrderService;

    public PayOrderController(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    @PostMapping("/submit")
    public CommonResult<PayOrderUnifiedRespDTO> submitPayOrder(@RequestBody PayOrderSubmitReqVO reqVO) {
        PayOrderUnifiedRespDTO respVO = payOrderService.submitPayOrder(reqVO);
        return success(respVO);
    }

}
