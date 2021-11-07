package com.insurance.app.domain;

import java.time.LocalDate;

public class CmTlActuarial {

    private String    security_type;           //保障区分
    private int       entry_age;               //加入年齢
    private String    insured_person_sex;      //性別
    private String    payment_method;          //払込方法
    private int       premium_payment_term;    //掛金払込期間
    private LocalDate reference_date;          //基準日
    private int       insurance_money;         //死亡保険金

    public String getSecurity_type() {
        return security_type;
    }
    public void setSecurity_type(String security_type) {
        this.security_type = security_type;
    }
    public int getEntry_age() {
        return entry_age;
    }
    public void setEntry_age(int entry_age) {
        this.entry_age = entry_age;
    }
    public String getInsured_person_sex() {
        return insured_person_sex;
    }
    public void setInsured_person_sex(String insured_person_sex) {
        this.insured_person_sex = insured_person_sex;
    }
    public String getPayment_method() {
        return payment_method;
    }
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    public int getPremium_payment_term() {
        return premium_payment_term;
    }
    public void setPremium_payment_term(int premium_payment_term) {
        this.premium_payment_term = premium_payment_term;
    }
    public LocalDate getReference_date() {
        return reference_date;
    }
    public void setReference_date(LocalDate reference_date) {
        this.reference_date = reference_date;
    }
    public int getInsurance_money() {
        return insurance_money;
    }
    public void setInsurance_money(int insurance_money) {
        this.insurance_money = insurance_money;
    }


}
