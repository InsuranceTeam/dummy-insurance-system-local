package com.insurance.app.domain;

public class WlSList {
    private int     insured_person_id;          //被保険者番号
    private String  insured_person_name_kanji;  //被保険者氏名（漢字）
    private String  insured_person_name_kana;   //被保険者氏名（ｶﾅ）
    private String  insured_person_birth_date;  //被保険者生年月日
    private String  insured_person_sex;         //被保険者性別
    private int     contract_id;                //契約番号
    private String  contract_start_date;        //契約開始日
    private String  contract_end_date;          //契約終了日

    public int getInsured_person_id() {
        return insured_person_id;
    }
    public void setInsured_person_id(int insured_person_id) {
        this.insured_person_id = insured_person_id;
    }
    public String getInsured_person_name_kanji() {
        return insured_person_name_kanji;
    }
    public void setInsured_person_name_kanji(String insured_person_name_kanji) {
        this.insured_person_name_kanji = insured_person_name_kanji;
    }
    public String getInsured_person_name_kana() {
        return insured_person_name_kana;
    }
    public void setInsured_person_name_kana(String insured_person_name_kana) {
        this.insured_person_name_kana = insured_person_name_kana;
    }
    public String getInsured_person_birth_date() {
        return insured_person_birth_date;
    }
    public void setInsured_person_birth_date(String insured_person_birth_date) {
        this.insured_person_birth_date = insured_person_birth_date;
    }
    public String getInsured_person_sex() {
        return insured_person_sex;
    }
    public void setInsured_person_sex(String insured_person_sex) {
        this.insured_person_sex = insured_person_sex;
    }
    public int getContract_id() {
        return contract_id;
    }
    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
    public String getContract_start_date() {
        return contract_start_date;
    }
    public void setContract_start_date(String contract_start_date) {
        this.contract_start_date = contract_start_date;
    }
    public String getContract_end_date() {
        return contract_end_date;
    }
    public void setContract_end_date(String contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

}
