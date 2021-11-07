package com.insurance.app.service;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.app.domain.CmTcActuarial;
import com.insurance.app.domain.CmTlActuarial;
import com.insurance.app.domain.CmWcActuarial;
import com.insurance.app.domain.CmWlActuarial;
import com.insurance.app.mapper.CmActuarialMapper;


@Service
public class CmActuarialService {

    @Autowired
    private CmActuarialMapper cmActuarialMapper;

    String errorInfo;

    //定期医療の掛金算出
    @Transactional
    public int findPremium(CmTcActuarial cmTcActuarial) throws BindingException, Exception{
        try {
            //掛金単価×（入院日額／１０００）を返却
            return cmActuarialMapper.findTcPremium(cmTcActuarial) * (cmTcActuarial.getHospitalization_money() / 1000);

        }catch(BindingException e){
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー" + ","
                        + "下記パラメータから取得できる数理テーブル（定期医療）は存在しません"  + ","
                        + "　保障区分　　　：" + cmTcActuarial.getSecurity_type()           + ","
                        + "　加入時年齢　　：" + cmTcActuarial.getEntry_age()               + ","
                        + "　性別　　　　　：" + cmTcActuarial.getInsured_person_sex()      + ","
                        + "　払込方法　　　：" + cmTcActuarial.getPayment_method()          + ","
                        + "　掛金払込期間　：" + cmTcActuarial.getPremium_payment_term()    + ","
                        + "　基準日　　　　：" + cmTcActuarial.getReference_date()          + ",";
            throw new BindingException(errorInfo,e);

        }catch(Exception e) {
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー,コンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //定期生命の掛金算出
    @Transactional
    public int findPremium(CmTlActuarial cmTlActuarial) throws BindingException, Exception{
        try {
            //掛金単価×（死亡保険金／１００）を返却
            return cmActuarialMapper.findTlPremium(cmTlActuarial) * (cmTlActuarial.getInsurance_money() / 100);

        }catch(BindingException e){
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー" + ","
                        + "下記パラメータから取得できる数理テーブル（定期生命）は存在しません"  + ","
                        + "　保障区分　　　：" + cmTlActuarial.getSecurity_type()           + ","
                        + "　加入時年齢　　：" + cmTlActuarial.getEntry_age()               + ","
                        + "　性別　　　　　：" + cmTlActuarial.getInsured_person_sex()      + ","
                        + "　払込方法　　　：" + cmTlActuarial.getPayment_method()          + ","
                        + "　掛金払込期間　：" + cmTlActuarial.getPremium_payment_term()    + ","
                        + "　基準日　　　　：" + cmTlActuarial.getReference_date()          + ",";
            throw new BindingException(errorInfo,e);

        }catch(Exception e) {
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー,コンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //終身医療の掛金算出
    @Transactional
    public int findPremium(CmWcActuarial cmWcActuarial) throws BindingException, Exception{
        try {
            //掛金単価×（入院日額／１０００）を返却
            return cmActuarialMapper.findWcPremium(cmWcActuarial) * (cmWcActuarial.getHospitalization_money() / 1000);

        }catch(BindingException e){
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー" + ","
                        + "下記パラメータから取得できる数理テーブル（終身医療）は存在しません"  + ","
                        + "　保障区分　　　：" + cmWcActuarial.getSecurity_type()           + ","
                        + "　加入時年齢　　：" + cmWcActuarial.getEntry_age()               + ","
                        + "　性別　　　　　：" + cmWcActuarial.getInsured_person_sex()      + ","
                        + "　払込方法　　　：" + cmWcActuarial.getPayment_method()          + ","
                        + "　掛金払込年齢　：" + cmWcActuarial.getPayment_expiration_age()  + ","
                        + "　基準日　　　　：" + cmWcActuarial.getReference_date()          + ",";
            throw new BindingException(errorInfo,e);

        }catch(Exception e) {
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー,コンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //終身生命の掛金算出
    @Transactional
    public int findPremium(CmWlActuarial cmWlActuarial) throws BindingException, Exception{
        try {
            //掛金単価×（死亡保険金／１００）を返却
            return cmActuarialMapper.findWlPremium(cmWlActuarial) * (cmWlActuarial.getInsurance_money() / 100);

        }catch(BindingException e){
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー" + ","
                        + "下記パラメータから取得できる数理テーブル（終身生命）は存在しません"  + ","
                        + "　保障区分　　　：" + cmWlActuarial.getSecurity_type()           + ","
                        + "　加入時年齢　　：" + cmWlActuarial.getEntry_age()               + ","
                        + "　性別　　　　　：" + cmWlActuarial.getInsured_person_sex()      + ","
                        + "　払込方法　　　：" + cmWlActuarial.getPayment_method()          + ","
                        + "　払込満了年齢　：" + cmWlActuarial.getPayment_expiration_age()  + ","
                        + "　基準日　　　　：" + cmWlActuarial.getReference_date()          + ",";
            throw new BindingException(errorInfo,e);

        }catch(Exception e) {
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー,コンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }
}
