package com.insurance.app.domain;

import lombok.Data;

@Data
public class WlPayment {
    private int payment_control_id; //支払管理番号
    private int payment_amount;     //支払額

    public WlPayment(int payment_amount) {
        this.payment_amount = payment_amount;
    }
}
