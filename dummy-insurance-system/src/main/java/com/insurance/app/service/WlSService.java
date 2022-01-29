package com.insurance.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.app.domain.WlSContract;
import com.insurance.app.domain.WlSList;
import com.insurance.app.mapper.WlSMapper;

@Service
public class WlSService {

    @Autowired
    private WlSMapper wlSMapper;

    String errorInfo;

    //一覧へ表示する終身生命契約の取得を行なう
    @Transactional
    public List<WlSList> searchWlSList(int insured_person_id) throws Exception {
        try {
            return wlSMapper.searchWlSList(insured_person_id);
        }catch(Exception e) {
            e.printStackTrace();
            errorInfo = editErrorWlSList(insured_person_id);
            throw new Exception(errorInfo,e);
        }
    }

    //契約照会へ表示する終身生命契約の取得を行なう
    @Transactional
    public List<WlSContract> getWlSContract(int insured_person_id, int contract_id) throws Exception {
        try {
            return wlSMapper.getWlSContract(insured_person_id, contract_id);
        }catch(Exception e) {
            e.printStackTrace();
            errorInfo = editErrorWlSContract(insured_person_id, contract_id);
            throw new Exception(errorInfo,e);
        }
    }

    //異常終了時の出力メッセージ（一覧表示用の終身生命契約取得エラー）
    private String editErrorWlSList(int insured_person_id) {
        return    "一覧表示用の終身生命契約取得エラー" +  ","
                + "被保険者番号　　：" + insured_person_id         +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（契約照会用の終身生命契約取得エラー）
    private String editErrorWlSContract(int insured_person_id, int contract_id) {
        return    "契約照会用の終身生命契約取得エラー" +  ","
                + "被保険者番号　　：" + insured_person_id         +  ","
                + "契約番号　　　　：" + contract_id               +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }
}

