package com.insurance.app.domain;

import java.time.LocalDate;

public interface CmKActuarial {

    //getterメソッド
    public LocalDate getInsured_person_birth_date(); //生年月日
    public String    getInsured_person_sex();        //性別
    public LocalDate getContract_start_date();       //契約開始日
    public LocalDate getContract_maturity_date();    //契約満期日
    public String    getSecurity_type();             //保障種類
    public int       getEntry_age();                 //加入年齢
    public String    getPayment_method();            //払込方法
    public int       getHospitalization_money();     //入院日額
    public int       getInsurance_money();           //保険金
    public int       getPremium();                   //掛金
    public int       getPremium_payment_term();      //掛金払込期間
    public int       getPayment_expiration_age();    //払込満了年齢
    public LocalDate getCancel_date();               //解約日
    public int       getCorresponding_age();         //応当日時点の年齢

    //setterメソッド
    public void      setInsured_person_birth_date(LocalDate insured_person_birth_date); //生年月日
    public void      setInsured_person_sex(String insured_person_sex);                  //性別
    public void      setContract_start_date(LocalDate contract_start_date);             //契約開始日
    public void      setContract_maturity_date(LocalDate contract_maturity_date);       //契約満期日
    public void      setSecurity_type(String security_type);                            //保障種類
    public void      setEntry_age(int entry_age);                                       //加入年齢
    public void      setPayment_method(String payment_method);                          //払込方法
    public void      setHospitalization_money(int hospitalization_money);               //入院日額
    public void      setInsurance_money(int insurance_money);                           //保険金
    public void      setPremium(int premium);                                           //掛金
    public void      setPremium_payment_term(int premium_payment_term);                 //掛金払込期間
    public void      setPayment_expiration_age(int payment_expiration_age);             //払込満了年齢
    public void      setCancel_date(LocalDate cancel_date);                             //解約日
    public void      setCorresponding_age(int corresponding_age);                       //応当日時点の年齢

}
