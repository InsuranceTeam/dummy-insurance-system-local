package com.insurance.app.domain;

import javax.validation.constraints.NotBlank;

public class WlInput {

    //被保険者氏名（漢字）・姓
    private String  insured_person_name_kanji_surname;

    //被保険者氏名（漢字）・名
    private String  insured_person_name_kanji_name;

    //被保険者氏名（カナ）
    @NotBlank(message="被保険者氏名の姓（カナ）をセイ欄を入力してください")
    private String  insured_person_name_kana_surname;

    //被保険者氏名（カナ）
    @NotBlank(message="被保険者氏名の名（カナ）をメイ欄を入力してください")
    private String  insured_person_name_kana_name;

    //生年月日
    @NotBlank(message="被保険者の生年月日を入力してください")
    private String  insured_person_birth_date;

    //性別
    private String  insured_person_sex;

    //加入日
    @NotBlank(message="終身生命に加入する日を入力してください")
    private String  start_date;

    //死亡保険金
    private int  insurance_money;

    //払込満了年齢
    private int  payment_expiration_age;

    //払込方法
    private String  payment_method;

    //被保険者氏名（漢字）・姓_getter
    public String getInsured_person_name_kanji_surname() {
        return insured_person_name_kanji_surname;
    }

    //被保険者氏名（漢字）・姓_setter
    public void setInsured_person_name_kanji_surname(String insured_person_name_kanji_surname) {
        this.insured_person_name_kanji_surname = insured_person_name_kanji_surname;
    }

    //被保険者氏名（漢字）・名_getter
    public String getInsured_person_name_kanji_name() {
        return insured_person_name_kanji_name;
    }

    //被保険者氏名（漢字）・名_setter
    public void setInsured_person_name_kanji_name(String insured_person_name_kanji_name) {
        this.insured_person_name_kanji_name = insured_person_name_kanji_name;
    }

    //被保険者氏名（カナ）・姓_getter
    public String getInsured_person_name_kana_surname() {
        return insured_person_name_kana_surname;
    }

    //被保険者氏名（カナ）・姓_setter
    public void setInsured_person_name_kana_surname(String insured_person_name_kana_surname) {
        this.insured_person_name_kana_surname = insured_person_name_kana_surname;
    }

    //被保険者氏名（カナ）・名_getter
    public String getInsured_person_name_kana_name() {
        return insured_person_name_kana_name;
    }

    //被保険者氏名（カナ）・姓_setter
    public void setInsured_person_name_kana_name(String insured_person_name_kana_name) {
        this.insured_person_name_kana_name = insured_person_name_kana_name;
    }

    //生年月日_getter
    public String getInsured_person_birth_date() {
        return insured_person_birth_date;
    }

    //生年月日_setter
    public void setInsured_person_birth_date(String insured_person_birth_date) {
        this.insured_person_birth_date = insured_person_birth_date;
    }

    //性別_getter
    public String getInsured_person_sex() {
        return insured_person_sex;
    }

    //性別_setter
    public void setInsured_person_sex(String insured_person_sex) {
        this.insured_person_sex = insured_person_sex;
    }

    //加入日_getter
    public String getStart_date() {
        return start_date;
    }

    //加入日_setter
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    //死亡保険金_getter
    public int getInsurance_money() {
        return insurance_money;
    }

    //死亡保険金_setter
    public void setInsurance_money(int insurance_money) {
        this.insurance_money = insurance_money;
    }

    //払込満了年齢_getter
    public int getPayment_expiration_age() {
        return payment_expiration_age;
    }

    //払込満了年齢_setter
    public void setPayment_expiration_age(int payment_expiration_age) {
        this.payment_expiration_age = payment_expiration_age;
    }

    //払込方法_getter
    public String getPayment_method() {
        return payment_method;
    }

    //払込方法_setter
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
