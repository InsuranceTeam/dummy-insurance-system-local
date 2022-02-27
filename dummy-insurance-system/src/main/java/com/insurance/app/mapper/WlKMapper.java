package com.insurance.app.mapper;

import com.insurance.app.domain.WlPayment;
import com.insurance.app.domain.WlPaymentIdx;
import com.insurance.app.domain.WlSContract;

public interface WlKMapper {

    void updateKContracts(WlSContract wlSContract);
    void insertKPayment(WlPayment wlPayment);
    void insertKPaymentIdx(WlPaymentIdx paymentIdx);
}
