package com.insurance.app.service;

import java.time.LocalDate;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.app.domain.WlContracts;
import com.insurance.app.domain.WlInsuredPersons;
import com.insurance.app.mapper.WlMapper;

@Service
public class WlService {

    @Autowired
    private WlMapper wlMapper;

    String errorInfo;

    //被保険者情報テーブルの名寄せ
    @Transactional
    public int nayoseInsuredPersons(String    insured_person_name_kana,
                                    LocalDate insured_person_birth_date,
                                    String    insured_person_sex) throws BindingException, Exception{
        try {
            //被保険者情報テーブルを名寄せにより、取得する
            return wlMapper.nayoseInsuredPersons(insured_person_name_kana,
                                                 insured_person_birth_date,
                                                 insured_person_sex);

        }catch(BindingException e){
            return 0;

        }catch(Exception e) {
            errorInfo =   "被保険者情報テーブル取得エラー（名寄せによる取得）" +  ","
                        + "被保険者氏名(ｶﾅ)：" + insured_person_name_kana      +  ","
                        + "生年月日　　　　：" + insured_person_birth_date     +  ","
                        + "性別　　　　　　：" + insured_person_sex            +  ","
                        + "詳細はコンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //被保険者情報テーブルの登録
    @Transactional
    public void insertInsuredPersons(WlInsuredPersons wlInsuredPersons) throws Exception{
        try {
            //被保険者情報テーブルに新規データを登録する
            wlMapper.insertInsuredPersons(wlInsuredPersons);
        }catch(Exception e) {
            errorInfo =   "被保険者情報テーブル登録エラー" +  ","
                        + "被保険者氏名(ｶﾅ)：" + wlInsuredPersons.getInsured_person_name_kana()  +  ","
                        + "生年月日　　　　：" + wlInsuredPersons.getInsured_person_birth_date() +  ","
                        + "性別　　　　　　：" + wlInsuredPersons.getInsured_person_sex()        +  ","
                        + "詳細はコンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //被保険者情報テーブルの更新
    @Transactional
    public void updateInsuredPersons(WlInsuredPersons wlInsuredPersons) throws Exception{
        try {
            //被保険者情報テーブルに新規データを登録する
            wlMapper.updateInsuredPersons(wlInsuredPersons);
        }catch(Exception e) {
            errorInfo =   "被保険者情報テーブル更新エラー" +  ","
                        + "被保険者番号　　：" + wlInsuredPersons.getInsured_person_id()         +  ","
                        + "被保険者氏名(ｶﾅ)：" + wlInsuredPersons.getInsured_person_name_kana()  +  ","
                        + "生年月日　　　　：" + wlInsuredPersons.getInsured_person_birth_date() +  ","
                        + "性別　　　　　　：" + wlInsuredPersons.getInsured_person_sex()        +  ","
                        + "詳細はコンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //契約番号の取得
    @Transactional
    public int getContractId(int insured_person_id) throws BindingException, Exception{
        try {
            //被保険者情報テーブルに新規データを登録する
            return wlMapper.getContractId(insured_person_id) + 1;
        }catch(BindingException e){
            return 10000;
        }catch(Exception e) {
            errorInfo =   "契約情報テーブル　契約番号取得エラー"    +  ","
                        + "被保険者番号　　：" + insured_person_id  +  ","
                        + "詳細はコンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //契約履歴番号の取得
//    @Transactional
//    public int getContractHistoryId(int insured_person_id, int contract_id) throws BindingException, Exception{
//        try {
//            //被保険者情報テーブルに新規データを登録する
//            return wlMapper.getContractHistoryId(insured_person_id, contract_id) + 1;
//        }catch(BindingException e){
//            return 10000;
//        }catch(Exception e) {
//            e.printStackTrace();
//            errorInfo =   "契約情報テーブル　契約履歴番号取得エラー" +  ","
//                        + "被保険者番号　　：" + insured_person_id   +  ","
//                        + "契約番号　　　　：" + contract_id         +  ","
//                        + "詳細はコンソール画面に出力されたログを参照願います";
//            throw new Exception(errorInfo,e);
//        }
//    }

    public void insertContracts(WlContracts wlContracts) throws Exception{
        try {
            //契約情報テーブルに新規データを登録する
            wlMapper.insertContracts(wlContracts);
        }catch(Exception e) {
            errorInfo =   "契約情報情報テーブル登録エラー" +  ","
                        + "被保険者番号　　：" + wlContracts.getInsured_person_id()      +  ","
                        + "契約番号　　　　：" + wlContracts.getContract_id()            +  ","
                        + "契約履歴番号　　：" + wlContracts.getContract_history_id()    +  ","
                        + "契約開始日　　　：" + wlContracts.getContract_start_date()    +  ","
                        + "契約終了日　　　：" + wlContracts.getContract_end_date()      +  ","
                        + "契約終了事由　　：" + wlContracts.getContract_end_reason()    +  ","
                        + "契約満期日　　　：" + wlContracts.getContract_maturity_date() +  ","
                        + "保障種類　　　　：" + wlContracts.getSecurity_type()          +  ","
                        + "加入年齢　　　　：" + wlContracts.getEntry_age()              +  ","
                        + "払込方法　　　　：" + wlContracts.getPayment_method()         +  ","
                        + "保険金　　　　　：" + wlContracts.getInsurance_money()        +  ","
                        + "掛金　　　　　　：" + wlContracts.getPremium()                +  ","
                        + "掛金払込期間　　：" + wlContracts.getPremium_payment_term()   +  ","
                        + "契約期間　　　　：" + wlContracts.getContract_term()          +  ","
                        + "払込満了年齢　　：" + wlContracts.getPayment_expiration_age() +  ","
                        + "詳細はコンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }
}
