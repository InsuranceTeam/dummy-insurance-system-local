package com.insurance.app.service;

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

    @Transactional(rollbackFor = Exception.class)
    public String entryContracts(WlInsuredPersons wlInsuredPersons, WlContracts wlContracts)
                                 throws BindingException, Exception {

        try {
            int    insured_person_id = 0;     //被保険者番号
            int    contract_id       = 10000; //契約番号
            String iuComment;                 //被保険者情報テーブルの登録／更新情報を設定

            //被保険者番号の編集を行なう
            try {
                //名寄せにより被保険者番号を取得する
                insured_person_id = wlMapper.nayoseInsuredPersons(wlInsuredPersons.getInsured_person_name_kana(),
                                                                  wlInsuredPersons.getInsured_person_birth_date(),
                                                                  wlInsuredPersons.getInsured_person_sex());
            }catch(BindingException e) {
                //名寄せで被保険者情報テーブルが取得できない場合は処理なし
            }catch(Exception e) {
                e.printStackTrace();
                errorInfo = editErrorNayoseInsuredPersons(wlInsuredPersons);
                throw new Exception(errorInfo,e);
            }


            //名寄せによる被保険者番号の取得結果に応じて、被保険者情報テーブルの登録・更新を行なう
            if(insured_person_id == 0) {
                try {
                  //新規の被保険者情報テーブルを登録する
                    wlMapper.insertInsuredPersons(wlInsuredPersons);
                    iuComment = "※登録";
                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorInsertInsuredPersons(wlInsuredPersons);
                    throw new Exception(errorInfo,e);
                }
            }else {
                try {
                    //既存の被保険者テーブルを更新する
                    wlInsuredPersons.setInsured_person_id(insured_person_id);
                    wlMapper.updateInsuredPersons(wlInsuredPersons);
                    iuComment = "※更新";
                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorUpdateInsuredPersons(wlInsuredPersons);
                    throw new Exception(errorInfo,e);
                }
            }


            //被保険者情報テーブル・被保険者番号を契約者情報テーブルの同項目に設定する
            wlContracts.setInsured_person_id(wlInsuredPersons.getInsured_person_id());


            //契約番号の編集を行なう
            try {
                //契約番号（登録済みの契約情報と重複しない番号を設定する）
                contract_id = wlMapper.getContractId(wlInsuredPersons.getInsured_person_id()) + 1;
            }catch(BindingException e) {
                //対象の被保険者配下に契約が存在しない場合は処理なし（契約番号は初期設定値の10000）
            }catch(Exception e) {
                e.printStackTrace();
                errorInfo = editErrorGetContractId(wlInsuredPersons.getInsured_person_id());
                throw new Exception(errorInfo,e);
            }
            wlContracts.setContract_id(contract_id);


            //契約情報テーブルに新規データを登録する
            try {
                wlMapper.insertContracts(wlContracts);
            }catch(Exception e) {
                e.printStackTrace();
                errorInfo = editErrorInsertContracts(wlContracts);
                throw new Exception(errorInfo,e);
            }

            return iuComment;

        }catch(Exception e) {
            throw new Exception(e);
        }

    }

    //異常終了時の出力メッセージ（被保険者情報テーブル取得エラー（名寄せによる取得））
    private String editErrorNayoseInsuredPersons(WlInsuredPersons wlInsuredPersons) {
        return  "被保険者情報テーブル取得エラー（名寄せによる取得）" +  ","
               + "被保険者氏名(ｶﾅ)：" + wlInsuredPersons.getInsured_person_name_kana()   +  ","
               + "生年月日　　　　：" + wlInsuredPersons.getInsured_person_birth_date()  +  ","
               + "性別　　　　　　：" + wlInsuredPersons.getInsured_person_sex()         +  ","
               + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（被保険者情報テーブル登録エラー）
    private String editErrorInsertInsuredPersons(WlInsuredPersons wlInsuredPersons) {
        return    "被保険者情報テーブル登録エラー" +  ","
                + "被保険者氏名(ｶﾅ)：" + wlInsuredPersons.getInsured_person_name_kana()  +  ","
                + "生年月日　　　　：" + wlInsuredPersons.getInsured_person_birth_date() +  ","
                + "性別　　　　　　：" + wlInsuredPersons.getInsured_person_sex()        +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（被保険者情報テーブル更新エラー）
    private String editErrorUpdateInsuredPersons(WlInsuredPersons wlInsuredPersons) {
        return    "被保険者情報テーブル更新エラー" +  ","
                + "被保険者番号　　：" + wlInsuredPersons.getInsured_person_id()         +  ","
                + "被保険者氏名(ｶﾅ)：" + wlInsuredPersons.getInsured_person_name_kana()  +  ","
                + "生年月日　　　　：" + wlInsuredPersons.getInsured_person_birth_date() +  ","
                + "性別　　　　　　：" + wlInsuredPersons.getInsured_person_sex()        +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（契約情報テーブル　契約番号取得エラー）
    private String editErrorGetContractId(int insured_person_id) {
        return    "契約情報テーブル　契約番号取得エラー"    +  ","
                + "被保険者番号　　：" + insured_person_id  +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（契約情報テーブル登録エラー）
    private String editErrorInsertContracts(WlContracts wlContracts) {
        return   "契約情報テーブル登録エラー" +  ","
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
    }
}
