package com.insurance.app.domain;

import java.time.LocalDateTime;

public class WlContracts {

    private int           insured_person_id;      //被保険者番号
    private int           contract_id;            //契約番号
    private int           contract_history_id;    //契約履歴番号
    private String        contract_start_date;    //契約開始日
    private String        contract_end_date;      //契約終了日
    private String        contract_end_reason;    //契約終了事由
    private String        contract_maturity_date; //契約満期日
    private String        security_type;          //保障種類
    private int           entry_age;              //加入年齢
    private String        payment_method;         //払込方法
    private int           insurance_money;        //保険金
    private int           premium;                //掛金
    private int           premium_payment_term;   //掛金払込期間
    private int           contract_term;          //契約期間
    private int           payment_expiration_age; //払込満了年齢
    private LocalDateTime create_at;              //作成日
    private LocalDateTime update_at;              //更新日

    //被保険者番号_getter
    public int getInsured_person_id() {
        return insured_person_id;
    }

    //被保険者番号_setter
    public void setInsured_person_id(int insured_person_id) {
        this.insured_person_id = insured_person_id;
    }

    //契約番号_getter
    public int getContract_id() {
        return contract_id;
    }

    //契約番号_setter
    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    //契約履歴番号_getter
    public int getContract_history_id() {
        return contract_history_id;
    }

    //契約履歴番号_setter
    public void setContract_history_id(int contract_history_id) {
        this.contract_history_id = contract_history_id;
    }

    //契約開始日_getter
    public String getContract_start_date() {
        return contract_start_date;
    }

    //契約開始日_setter
    public void setContract_start_date(String contract_start_date) {
        this.contract_start_date = contract_start_date;
    }

    //契約終了日_getter
    public String getContract_end_date() {
        return contract_end_date;
    }

    //契約終了日_setter
    public void setContract_end_date(String contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    //契約終了事由_getter
    public String getContract_end_reason() {
        return contract_end_reason;
    }

    //契約終了事由_setter
    public void setContract_end_reason(String contract_end_reason) {
        this.contract_end_reason = contract_end_reason;
    }

    //契約満期日_getter
    public String getContract_maturity_date() {
        return contract_maturity_date;
    }

    //契約満期日_setter
    public void setContract_maturity_date(String contract_maturity_date) {
        this.contract_maturity_date = contract_maturity_date;
    }

    //保障種類_getter
    public String getSecurity_type() {
        return security_type;
    }

    //保障種類_setter
    public void setSecurity_type(String security_type) {
        this.security_type = security_type;
    }

    //加入年齢_getter
    public int getEntry_age() {
        return entry_age;
    }

    //加入年齢_setter
    public void setEntry_age(int entry_age) {
        this.entry_age = entry_age;
    }

    //払込方法_getter
    public String getPayment_method() {
        return payment_method;
    }

    //払込方法_setter
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    //保険金_getter
    public int getInsurance_money() {
        return insurance_money;
    }

    //保険金_setter
    public void setInsurance_money(int insurance_money) {
        this.insurance_money = insurance_money;
    }

    //掛金_getter
    public int getPremium() {
        return premium;
    }

    //掛金_setter
    public void setPremium(int premium) {
        this.premium = premium;
    }

    //掛金払込期間_getter
    public int getPremium_payment_term() {
        return premium_payment_term;
    }

    //掛金払込期間_setter
    public void setPremium_payment_term(int premium_payment_term) {
        this.premium_payment_term = premium_payment_term;
    }

    //契約期間_getter
    public int getContract_term() {
        return contract_term;
    }

    //契約期間_setter
    public void setContract_term(int contract_term) {
        this.contract_term = contract_term;
    }

    //払込満了年齢_getter
    public int getPayment_expiration_age() {
        return payment_expiration_age;
    }

    //払込満了年齢_setter
    public void setPayment_expiration_age(int payment_expiration_age) {
        this.payment_expiration_age = payment_expiration_age;
    }

    //作成日_getter
    public LocalDateTime getCreate_at() {
        return create_at;
    }

    //作成日_setter
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    //更新日_getter
    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    //更新日_setter
    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }
}
