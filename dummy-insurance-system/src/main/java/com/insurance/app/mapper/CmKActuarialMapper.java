package com.insurance.app.mapper;

import com.insurance.app.domain.CmKActuarial;
import com.insurance.app.domain.CmKTcActuarial;
import com.insurance.app.domain.CmKTlActuarial;
import com.insurance.app.domain.CmKWcActuarial;
import com.insurance.app.domain.CmKWlActuarial;

public interface CmKActuarialMapper {

    //解約返戻金算出
    int[] cancellationRefundCalculation(CmKActuarial cmKActuarial);

    //定期医療の解約返戻金算出
    int[] cancellationRefundCalculation(CmKTcActuarial cmKTcActuarial);

    //定期生命の解約返戻金算出
    int[] cancellationRefundCalculation(CmKTlActuarial cmKTlActuarial);

    //終身医療の解約返戻金算出
    int[] cancellationRefundCalculation(CmKWcActuarial cmKWcActuarial);

    //終身生命の解約返戻金算出
    int[] cancellationRefundCalculation(CmKWlActuarial cmKWlActuarial);
}
