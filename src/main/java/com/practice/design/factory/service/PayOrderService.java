package com.practice.design.factory.service;

import com.practice.design.factory.entity.PayOrderSubmitReqVO;
import com.practice.design.factory.entity.PayOrderSubmitRespVO;
import com.practice.design.factory.entity.PayOrderUnifiedRespDTO;

public interface PayOrderService {
    PayOrderUnifiedRespDTO submitPayOrder(PayOrderSubmitReqVO reqVO);
}
