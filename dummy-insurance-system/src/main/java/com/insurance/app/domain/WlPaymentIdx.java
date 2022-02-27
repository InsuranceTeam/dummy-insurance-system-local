package com.insurance.app.domain;

import lombok.Data;

@Data
public class WlPaymentIdx {
    private int insured_person_id;  //被保険者番号
    private int contract_id;        //契約番号
    private int payment_control_id; //支払管理番号

    public WlPaymentIdx(int insured_person_id, int contract_id, int payment_control_id) {
        this.insured_person_id  = insured_person_id;
        this.contract_id        = contract_id;
        this.payment_control_id = payment_control_id;
    }
}
