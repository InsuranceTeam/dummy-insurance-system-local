package com.insurance.app.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.app.domain.WlKConfimation;
import com.insurance.app.domain.WlPayment;
import com.insurance.app.domain.WlPaymentIdx;
import com.insurance.app.domain.WlSContract;
import com.insurance.app.mapper.WlKMapper;

@Service
public class WlKService {

    @Autowired
    private WlKMapper wlKMapper;

    String errorInfo;

    @Transactional(rollbackFor = Exception.class)
    public String update(WlKConfimation wlKConfimation)
                                 throws BindingException, Exception {

        try {

            WlSContract wlSContract = wlKConfimation.getWlSContract();
            WlPayment wlPayment = new WlPayment(wlKConfimation.getCancellation_refund());
            WlPaymentIdx wlPaymentIdx = null;

            if(wlKConfimation.getSelect_cancel().equals("1")) {
                //解約処理に応じた更新処理を行なう

                try {
                    //契約情報テーブルの更新を行なう
                    wlSContract.setContract_end_date(wlKConfimation.getCancel_date().replace("/", ""));
                    wlSContract.setContract_end_reason("K0");
                    wlKMapper.updateKContracts(wlSContract);
                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorUpdateKContracts(wlSContract);
                    throw new Exception(errorInfo,e);
                };

                //支払情報テーブルの登録を行なう
                try {
                    wlKMapper.insertKPayment(wlPayment);

                    //支払情報を照会情報エリアに設定する
                    wlSContract.setPayment_control_id(wlPayment.getPayment_control_id());
                    wlSContract.setPayment_amount(wlPayment.getPayment_amount());

                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorInsertKPayment(wlSContract);
                    throw new Exception(errorInfo,e);
                };

                try {
                    //支払情報インデックステーブルの登録を行なう
                    wlPaymentIdx = new WlPaymentIdx(wlSContract.getInsured_person_id(),
                                                    wlSContract.getContract_id(),
                                                    wlPayment.getPayment_control_id());
                    wlKMapper.insertKPaymentIdx(wlPaymentIdx);
                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorInsertKPaymentIdx(wlPaymentIdx);
                    throw new Exception(errorInfo,e);
                };

                return "解約処理が完了しました";

            }else {
                //取消処理に応じた更新処理を行なう

                try {
                    //契約情報テーブルの更新を行なう（契約終了日は契約発効日の前日を設定する）
                    LocalDate start_date  = LocalDate.parse(wlSContract.getContract_start_date(),
                                             DateTimeFormatter.ofPattern("uuuuMMdd"));
                    LocalDate cancel_date = start_date.minusDays(1);
                    wlSContract.setContract_end_date(cancel_date.format(DateTimeFormatter.ofPattern("uuuuMMdd")));
                    wlSContract.setContract_end_reason("K2");
                    wlKMapper.updateKContracts(wlSContract);
                }catch(Exception e) {
                    e.printStackTrace();
                    errorInfo = editErrorUpdateKContracts(wlSContract);
                    throw new Exception(errorInfo,e);
                };

                return "取消処理が完了しました";
            }

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //異常終了時の出力メッセージ（契約情報情報テーブル更新エラー）
    private String editErrorUpdateKContracts(WlSContract wlSContract) {
        return   "契約情報テーブル更新エラー" +  ","
                + "被保険者番号　　：" + wlSContract.getInsured_person_id()      +  ","
                + "契約番号　　　　：" + wlSContract.getContract_id()            +  ","
                + "契約履歴番号　　：" + wlSContract.getContract_history_id()    +  ","
                + "契約開始日　　　：" + wlSContract.getContract_start_date()    +  ","
                + "契約終了日　　　：" + wlSContract.getContract_end_date()      +  ","
                + "契約終了事由　　：" + wlSContract.getContract_end_reason()    +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（支払情報テーブル登録エラー）
    private String editErrorInsertKPayment(WlSContract wlSContract) {
        return    "支払情報テーブル更新エラー" +  ","
                + "被保険者番号　　：" + wlSContract.getInsured_person_id()      +  ","
                + "契約番号　　　　：" + wlSContract.getContract_id()            +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }

    //異常終了時の出力メッセージ（支払情報インデックステーブル登録エラー）
    private String editErrorInsertKPaymentIdx(WlPaymentIdx wlPaymentIdx) {
        return    "支払情報インデックステーブル更新エラー" +  ","
                + "被保険者番号　　：" + wlPaymentIdx.getInsured_person_id()      +  ","
                + "契約番号　　　　：" + wlPaymentIdx.getContract_id()            +  ","
                + "支払管理番号　　：" + wlPaymentIdx.getPayment_control_id()     +  ","
                + "詳細はコンソール画面に出力されたログを参照願います";
    }
}
