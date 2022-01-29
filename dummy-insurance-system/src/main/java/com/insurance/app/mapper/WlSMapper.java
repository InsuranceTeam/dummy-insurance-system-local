package com.insurance.app.mapper;

import java.util.List;

import com.insurance.app.domain.WlSContract;
import com.insurance.app.domain.WlSList;

public interface WlSMapper {

    List<WlSList>     searchWlSList(int insured_person_id);
    List<WlSContract> getWlSContract(int insured_person_id, int contract_id);
}
