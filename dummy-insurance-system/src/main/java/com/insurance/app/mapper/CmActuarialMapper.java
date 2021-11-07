package com.insurance.app.mapper;

import com.insurance.app.domain.CmTcActuarial;
import com.insurance.app.domain.CmTlActuarial;
import com.insurance.app.domain.CmWcActuarial;
import com.insurance.app.domain.CmWlActuarial;

//@Mapper
public interface CmActuarialMapper {

    //定期医療の掛金取得
    int findTcPremium(CmTcActuarial cmTcActuarial);

    //定期生命の掛金取得
    int findTlPremium(CmTlActuarial cmTlActuarial);

    //終身医療の掛金取得
    int findWcPremium(CmWcActuarial cmWcActuarial);

    //終身生命の掛金取得
    int findWlPremium(CmWlActuarial cmWlActuarial);
}

