package com.insurance.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CmKWlActuarial implements CmKActuarial {
    private LocalDate insured_person_birth_date;  //生年月日
    private String    insured_person_sex;         //性別
    private LocalDate contract_start_date;        //契約開始日
    private LocalDate contract_maturity_date;     //契約満期日
    private String    security_type;              //保障種類
    private int       entry_age;                  //加入年齢
    private String    payment_method;             //払込方法
    private int       hospitalization_money;      //入院日額（編集不要）
    private int       insurance_money;            //死亡保険金
    private int       premium;                    //掛金
    private int       premium_payment_term;       //掛金払込期間
    private int       payment_expiration_age;     //払込満了年齢
    private LocalDate cancel_date;                //解約日
    private int       corresponding_age;          //応当日時点の年齢（編集不要（数理モジュール内で設定する項目））
}
