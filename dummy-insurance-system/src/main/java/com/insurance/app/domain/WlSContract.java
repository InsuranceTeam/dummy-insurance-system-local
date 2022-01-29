package com.insurance.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WlSContract {
    private int             insured_person_id;          //被保険者番号
    private String          insured_person_name_kanji;  //被保険者氏名（漢字）
    private String          insured_person_name_kana;   //被保険者氏名（カナ）
    private String          insured_person_birth_date;  //生年月日
    private String          insured_person_sex;         //性別
    private LocalDateTime   ip_update_at;               //更新日（被保険者情報）
    private int             contract_id;                //契約番号
    private int             contract_history_id;        //契約履歴番号
    private String          contract_start_date;        //契約開始日
    private String          contract_end_date;          //契約終了日
    private String          contract_end_reason;        //契約終了事由
    private String          contract_maturity_date;     //契約満期日
    private String          security_type;              //保障種類
    private int             entry_age;                  //加入年齢
    private String          payment_method;             //払込方法
    private int             insurance_money;            //保険金
    private int             premium;                    //掛金
    private int             premium_payment_term;       //掛金払込期間
    private int             contract_term;              //契約期間
    private int             payment_expiration_age;     //払込満了年齢
    private LocalDateTime   c_update_at;                //更新日（契約情報）
    private int             payment_control_id;         //支払管理番号
    private int             payment_amount;             //支払額
    private LocalDateTime   p_update_at;                //更新日（支払情報）

}
