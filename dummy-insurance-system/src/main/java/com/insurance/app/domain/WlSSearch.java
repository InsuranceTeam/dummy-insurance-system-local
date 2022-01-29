package com.insurance.app.domain;

import javax.validation.constraints.NotBlank;

public class WlSSearch {

    //被保険者番号（文字列）
    @NotBlank(message="被保険者番号を入力してください")
    private String  insured_person_id_str = "";

    //被保険者番号(long)
    private long    insured_person_id_long = 0;

    //被保険者番号
    private int     insured_person_id = 0;

    public String getInsured_person_id_str() {
        return insured_person_id_str;
    }

    public void setInsured_person_id_str(String insured_person_id_str) {
        this.insured_person_id_str = insured_person_id_str;
    }

    public long getInsured_person_id_long() {
        return insured_person_id_long;
    }

    public void setInsured_person_id_long(long insured_person_id_long) {
        this.insured_person_id_long = insured_person_id_long;
    }

    public int getInsured_person_id() {
        return insured_person_id;
    }

    public void setInsured_person_id(int insured_person_id) {
        this.insured_person_id = insured_person_id;
    }
}
