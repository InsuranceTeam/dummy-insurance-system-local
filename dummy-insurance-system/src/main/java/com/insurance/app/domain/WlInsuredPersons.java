package com.insurance.app.domain;

import java.time.LocalDateTime;

public class WlInsuredPersons {

    private int             insured_person_id;          //被保険者番号
    private String          insured_person_name_kanji;  //被保険者氏名（漢字）
    private String          insured_person_name_kana;   //被保険者氏名（カナ）
    private String          insured_person_birth_date;  //生年月日
    private String          insured_person_sex;         //性別
    private LocalDateTime   create_at;                  //作成日
    private LocalDateTime   update_at;                  //更新日

    //被保険者番号_getter
    public int getInsured_person_id() {
        return insured_person_id;
    }

    //被保険者番号_setter
    public void setInsured_person_id(int insured_person_id) {
        this.insured_person_id = insured_person_id;
    }

    //被保険者氏名（漢字）_getter
    public String getInsured_person_name_kanji() {
        return insured_person_name_kanji;
    }

    //被保険者氏名（漢字）_setter
    public void setInsured_person_name_kanji(String insured_person_name_kanji) {
        this.insured_person_name_kanji = insured_person_name_kanji;
    }

    //被保険者氏名（カナ）_getter
    public String getInsured_person_name_kana() {
        return insured_person_name_kana;
    }

    //被保険者氏名（カナ）_setter
    public void setInsured_person_name_kana(String insured_person_name_kana) {
        this.insured_person_name_kana = insured_person_name_kana;
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

    //作成日_ getter
    public LocalDateTime getCreate_at() {
        return create_at;
    }

    //作成日_ setter
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    //更新日_ getter
    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    //更新日_setter
    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }


}
