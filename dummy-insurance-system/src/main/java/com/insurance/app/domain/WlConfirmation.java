package com.insurance.app.domain;

public class WlConfirmation{

    private String  insured_person_name_kanji_surname; //被保険者氏名（漢字）・姓
    private String  insured_person_name_kanji_name;    //被保険者氏名（漢字）・名
    private String  insured_person_name_kana_surname;  //被保険者氏名（カナ）
    private String  insured_person_name_kana_name;     //被保険者氏名（カナ）
    private String  insured_person_birth_date;         //生年月日
    private String  insured_person_sex;                //性別
    private String  start_date;                        //加入日
    private int     insurance_money;                   //死亡保険金
    private int     payment_expiration_age;            //払込満了年齢
    private String  payment_method;                    //払込方法
    private int     entry_age;                         //加入年齢
    private int     premium;                           //掛金

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

    //加入年齢_getter
    public int getEntry_age() {
        return entry_age;
    }

  //加入年齢_setter
    public void setEntry_age(int entry_age) {
        this.entry_age = entry_age;
    }

    //掛金_getter
    public int getPremium() {
        return premium;
    }

    //掛金_setter
    public void setPremium(int premium) {
        this.premium = premium;
    }

    //入力画面項目_setter
    public void setWlInput(WlInput wlInput) {
        setInsured_person_name_kanji_surname(wlInput.getInsured_person_name_kanji_surname());
        setInsured_person_name_kanji_name(wlInput.getInsured_person_name_kanji_name());
        setInsured_person_name_kana_surname(wlInput.getInsured_person_name_kana_surname());
        setInsured_person_name_kana_name(wlInput.getInsured_person_name_kana_name());
        setInsured_person_birth_date(wlInput.getInsured_person_birth_date());
        setInsured_person_sex(wlInput.getInsured_person_sex());
        setStart_date(wlInput.getStart_date());
        setInsurance_money(wlInput.getInsurance_money());
        setPayment_expiration_age(wlInput.getPayment_expiration_age());
        setPayment_method(wlInput.getPayment_method());
    };
}
