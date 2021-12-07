package com.insurance.app.mapper;

import com.insurance.app.domain.WlContracts;
import com.insurance.app.domain.WlInsuredPersons;

public interface WlMapper {
    int nayoseInsuredPersons(String insured_person_name_kana,
                             String insured_person_birth_date,
                             String insured_person_sex);

    void insertInsuredPersons(WlInsuredPersons wlInsuredPersons);

    void updateInsuredPersons(WlInsuredPersons wlInsuredPersons);

    int getContractId(int insured_person_id);

    void insertContracts(WlContracts wlContracts);
}
