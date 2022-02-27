package com.insurance.app.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.app.domain.CmKActuarial;
import com.insurance.app.mapper.CmKActuarialMapper;

@Service
public class CmKActuarialService {

    @Autowired
    private CmKActuarialMapper cmKActuarialMapper;

    String errorInfo;
    Map<String, String> errorMap = new HashMap<String, String>(){
        {
            put("TC","定期医療");
            put("TL","定期生命");
            put("WC","終身医療");
            put("WL","終身生命");
        }
    };
    final String PAYMENT_MONTH = "1";     //月払
    final String PAYMENT_YEAR  = "3";     //年払

    //終身生命の解約返戻金算出
    @Transactional
    public int cancellationRefundCalculation(CmKActuarial cmKActuarial) throws Exception{

        LocalDate birth_date                = cmKActuarial.getInsured_person_birth_date();  //生年月日
        String    insured_person_sex        = cmKActuarial.getInsured_person_sex();         //性別
        LocalDate contract_start_date       = cmKActuarial.getContract_start_date();        //契約開始日
        String    security_type             = cmKActuarial.getSecurity_type();              //保障種類
        int       entry_age                 = cmKActuarial.getEntry_age();                  //加入年齢
        String    payment_method            = cmKActuarial.getPayment_method();             //払込方法
        int       premium_payment_term      = cmKActuarial.getPremium_payment_term();       //掛金払込期間
        int       payment_expiration_age    = cmKActuarial.getPayment_expiration_age();     //払込満了年齢
        LocalDate cancel_date               = cmKActuarial.getCancel_date();                //解約日

        try {
            //解約返戻金算出_前処理を行なう
            Map<String, Object> preprocessing_map
                                    = cancellationRefundPreprocessing(birth_date, contract_start_date,
                                                                      cancel_date, security_type, premium_payment_term,
                                                                      payment_expiration_age);

            //解約返戻金算出_本処理を行なう
            cmKActuarial.setCorresponding_age((int)preprocessing_map.get("corresponding_age"));
            return cancellationRefundMainProcessing( (int)preprocessing_map.get("refund_flg"), cmKActuarial,
                                                     (LocalDate)preprocessing_map.get("corresponding_date_bf"));

        }catch(Exception e){
            e.printStackTrace();
            errorInfo =   "数理モジュールエラー" + ","
                        + "下記パラメータを用いて数理テーブル（"
                        + errorMap.get(security_type)
                        + "）＿年齢別へアクセスしました。"  + ","
                        + "　保障区分　　　：" + security_type           + ","
                        + "　加入時年齢　　：" + entry_age               + ","
                        + "　性別　　　　　：" + insured_person_sex      + ","
                        + "　払込方法　　　：" + payment_method          + ","
                        + "　掛金払込期間　：" + premium_payment_term    + "　※定期保障で使用する項目,"
                        + "　払込満了年齢　：" + payment_expiration_age  + "　※終身保障で使用する項目,"
                        + "　解約日　　　　：" + cancel_date             + ","
                        + "その他の詳細は、コンソール画面に出力されたログを参照願います";
            throw new Exception(errorInfo,e);
        }
    }

    //-------------------------------------------------------------------------------------------------
    //解約返戻金算出に関する前処理を実施する
    //-------------------------------------------------------------------------------------------------
    private Map<String, Object> cancellationRefundPreprocessing(LocalDate birth_date, LocalDate contract_start_date,
                                                                 LocalDate cancel_date, String  security_type,
                                                                 int premium_payment_term, int payment_expiration_age){


        //「解約日の直近直前にあたる契約開始日の応当日」とその前日を算出する
        LocalDate[] corresponding_dates
                                = calculationCorrespondingDate(contract_start_date, cancel_date);
        LocalDate corresponding_date    = corresponding_dates[0];
        LocalDate corresponding_date_bf = corresponding_dates[1];

        //上記応当日時点の年齢を算出する
        int corresponding_age = Period.between(birth_date, corresponding_date).getYears();

        //解約返戻金の算出方法を判定する
        //(refund_flg：0⇒通常の解約返戻金算出、1⇒60歳満了_払込期間中の満期解約に対する解約返戻金算出、
        //             2⇒60歳満了_払込満了後の解約返戻金算出、3⇒上記応当日時点の年齢が120歳以上)
        int refund_flg = 0;
        if(security_type.charAt(0) == 'T') {
          //定期系保障は通常の解約返戻金算出方法のみ使用する
            refund_flg = 0;
        }else if(corresponding_age >= 120) {
            //上記応当日時点の年齢が120歳以上（解約返戻金は0円）
            refund_flg = 3;

        }else if(payment_expiration_age == 99) {

            //終身払は通常の解約返戻金算出
            refund_flg = 0;

        }else {
            //※６０歳満了時点の払込満了日を求める
            LocalDate payment_maturity_date = contract_start_date.plusYears(premium_payment_term).minusDays(1);

            if(cancel_date.compareTo(payment_maturity_date) <= 0) {

                //※６０歳満了は端数の関係で、満期解約時の解約返戻金が正しく求まらない。
                //そのため、個別の処理を行なう必要がある

                //満期解約であるかどうか判定する
                if(decisionMaturityCancel(cancel_date, payment_maturity_date, 1)) {
                    //60歳満了_払込期間中の満期解約に対する解約返戻金算出
                    refund_flg = 1;
                }else {
                    //上記以外は通常の解約返戻金算出
                    refund_flg = 0;
                }
            }else {
            //払込満了後の解約返戻金算出
            refund_flg = 2;
            }
        }

        //返却するMapを編集する
        Map<String, Object> return_map = new HashMap<String, Object>();
        return_map.put("corresponding_date"   , corresponding_date);
        return_map.put("corresponding_date_bf", corresponding_date_bf);
        return_map.put("corresponding_age"    , corresponding_age);
        return_map.put("refund_flg"           , refund_flg);

        return return_map;
    }

    //-------------------------------------------------------------------------------------------------
    //解約返戻金を算出する
    //-------------------------------------------------------------------------------------------------
    private int cancellationRefundMainProcessing(int refund_flg, CmKActuarial cmKActuarial,
                                                 LocalDate corresponding_date_bf){

        //解約返戻金算出に必要なエリアを設定する
        int       cancellation_refund         = 0;  //解約返戻金
        int       payment_premium             = 0;  //払込掛金
        int       subscription_period_premium = 0;  //加入期間に必要な掛金
        int[]     premium_ages                = null;
        int       age_max                     = 0;
        int       premium_age_max_month       = 0;
        int       premium_num                 = 0; //口数（入院日額：1000円／死亡保険金：100万円あたり、1口）
        String    security_type               = cmKActuarial.getSecurity_type();      //保障種類
        int       entry_age                   = cmKActuarial.getEntry_age();          //加入年齢
        String    payment_method              = cmKActuarial.getPayment_method();     //払込方法
        int       premium                     = cmKActuarial.getPremium();            //掛金
        LocalDate cancel_date                 = cmKActuarial.getCancel_date();        //解約日
        int       corresponding_age           = cmKActuarial.getCorresponding_age();  //応当日時点の年齢

        if(security_type.charAt(1) == 'C') {
            premium_num = cmKActuarial.getHospitalization_money() / 1000;
        }else{
            premium_num = cmKActuarial.getInsurance_money() / 1000000;
        }

        //解約返戻金の算出処理
        if(refund_flg == 0) {
          //定期保障、終身保障の終身払　または　終身保障の60歳満了_払込期間中（満期解約以外）に対する解約返戻金算出

            //各年齢ごとの掛金単価を取得する
            premium_ages = cmKActuarialMapper.cancellationRefundCalculation(cmKActuarial);
            age_max = premium_ages.length - 1;

            //年払の場合、「加入期間に必要な掛金」を求めるために必要となる
            //「解約日の直近直前にあたる契約開始日の応当日」時点の年齢に対する月払掛金の取得処理を行なう
            if(payment_method.equals(PAYMENT_YEAR)) {
                cmKActuarial.setEntry_age(corresponding_age);
                cmKActuarial.setPayment_method(PAYMENT_MONTH);
                premium_age_max_month = cmKActuarialMapper.cancellationRefundCalculation(cmKActuarial)[0];
            }

            //払込掛金（payment_premium）の算出
            payment_premium
                    = cancellationPaymentPremium(premium, age_max, payment_method, corresponding_date_bf,
                                                 cancel_date);

            //加入期間に必要な掛金（subscription_period_premium）の算出
            subscription_period_premium
                    = cancellationSubscriptionPeriodPpremium(premium_ages, age_max, payment_method,
                                                             corresponding_date_bf, cancel_date, premium_num,
                                                             premium_age_max_month);

        }else if(refund_flg == 1 || refund_flg == 2){

          //終身保障の６０歳満了_払込期間中の満期解約　または　払込満了後に対する解約返戻金算出

            //６０歳以降の保障を受けるために必要な掛金を払込掛金として算出する
            cmKActuarial.setEntry_age(60);
            cmKActuarial.setCorresponding_age(119);
            premium_ages = cmKActuarialMapper.cancellationRefundCalculation(cmKActuarial);

            //応当日時点の年齢までの掛金単価を合算する
            int unit_premium_age = 0;
            for(int i = 0; i < premium_ages.length; i++) {
                unit_premium_age += premium_ages[i];
            }

            //払込掛金（payment_premium）の算出
            int premium_age = unit_premium_age * premium_num;
            payment_premium = payment_method.equals(PAYMENT_MONTH) ? premium_age * 12 : premium_age;

            //加入期間に必要な掛金（subscription_period_premium）の算出
            if(refund_flg == 1) {
                //６０歳満了_払込期間中の満期解約の場合、払込掛金をすべて解約返戻金とする
                subscription_period_premium = 0;
            }else {
                //60歳満了_払込満了後の場合、
                //満期満了後～解約日時点までで必要な掛金を「加入期間に必要な掛金」として算出する
                cmKActuarial.setCorresponding_age(corresponding_age);
                premium_ages = cmKActuarialMapper.cancellationRefundCalculation(cmKActuarial);
                age_max = premium_ages.length - 1;

                //年払の場合、加入期間に必要な掛金を求めるために必要な
                //「解約日の直近直前にあたる契約開始日の応当日」時点の年齢に対する月払掛金の取得処理を行なう
                if(payment_method.equals(PAYMENT_YEAR)) {
                    cmKActuarial.setEntry_age(corresponding_age);
                    cmKActuarial.setPayment_method(PAYMENT_MONTH);
                    premium_age_max_month = cmKActuarialMapper.cancellationRefundCalculation(cmKActuarial)[0];
                }

                //加入期間に必要な掛金（subscription_period_premium）の算出
                subscription_period_premium
                        = cancellationSubscriptionPeriodPpremium(premium_ages, age_max, payment_method,
                                                                 corresponding_date_bf, cancel_date,
                                                                 premium_num, premium_age_max_month);
            }
        }

        //一時的に変更したエリアを元に戻す
        cmKActuarial.setEntry_age(entry_age);
        cmKActuarial.setPayment_method(payment_method);
        cmKActuarial.setCorresponding_age(corresponding_age);

        //解約返戻金（＝払込掛金 － 加入期間に必要な掛金）を返却
        cancellation_refund = payment_premium - subscription_period_premium;
//        System.out.printf("解約返戻金：%d\n"          , cancellation_refund);
//        System.out.printf("払込掛金：%d\n"            , payment_premium);
//        System.out.printf("加入期間に必要な掛金：%d\n", subscription_period_premium);
        return cancellation_refund;
    }


    //-------------------------------------------------------------------------------------------------
    //満期解約の判定処理（満期解約の場合、trueを返却する）
    //-------------------------------------------------------------------------------------------------
    private Boolean decisionMaturityCancel(LocalDate cancel_date, LocalDate payment_maturity_date, int month){

        //満期日の指定月数前の翌日を算出する
        LocalDate reference_date_month_ago_af = payment_maturity_date.minusMonths(month).plusDays(1);

        //満期解約の判定を行なう
        if(cancel_date.compareTo(reference_date_month_ago_af) >= 0 &&
           cancel_date.compareTo(payment_maturity_date)      <= 0) {
            return true;
        }else {
            return false;
        }
    }
    //-------------------------------------------------------------------------------------------------
    //「基準日の直近直前にあたる開始日の応当日」 とその前日を算出する
    //-------------------------------------------------------------------------------------------------
    private LocalDate[] calculationCorrespondingDate(LocalDate start_date, LocalDate reference_date){
        //開始日と基準日の期間を算出する
        Period period = Period.between(start_date, reference_date);

        //「開始日＋上記で算出した期間（年）」により、「基準日の直近直前にあたる開始日の応当日」を求める
        LocalDate corresponding_date = start_date.plusYears(period.getYears());
        LocalDate corresponding_date_bf;

        //上記応当日（基準日の直近直前にあたる開始日の応当日）の前日を求める。
        //ただし、開始日がうるう年の2/29である場合、応当日の月日が2/28となるため、
        //上記応当日を応当日前日とし、翌日（3/1）を上記応当日とする
        if(corresponding_date.getDayOfMonth() == start_date.getDayOfMonth()) {
            corresponding_date_bf = corresponding_date.minusDays(1);
        }else {
            corresponding_date_bf = corresponding_date.minusDays(0);
            corresponding_date    = corresponding_date.plusDays(1);
        }
        LocalDate[] corresponding_dates = {corresponding_date, corresponding_date_bf};
        return corresponding_dates;

    }

    //-------------------------------------------------------------------------------------------------
    //払込掛金（payment_premium）の算出
    //-------------------------------------------------------------------------------------------------
    private int cancellationPaymentPremium(int premium, int age_max, String payment_method,
                                           LocalDate corresponding_date_bf, LocalDate cancel_date) {
        //応当日時点の年齢－１歳までの掛金単価を合算する
        int premium_age = 0;
        for(int i = 0; i < age_max; i++) {
            premium_age += premium;
        }

        return cancellationUnitPricePremium(premium_age, premium, payment_method, corresponding_date_bf,
                                            cancel_date, 0);
    }

    //-------------------------------------------------------------------------------------------------
    //加入期間に必要な掛金（subscription_period_premium）の算出
    //-------------------------------------------------------------------------------------------------
    private int cancellationSubscriptionPeriodPpremium(int[] premium_ages, int age_max,
                                                       String payment_method, LocalDate corresponding_date_bf,
                                                       LocalDate cancel_date, int premium_num,
                                                       int premium_age_max_month) {

        //応当日時点の年齢－１歳までの掛金単価を合算する
        int premium_age = 0;
        for(int i = 0; i < age_max; i++) {
            premium_age += premium_ages[i];
        }

        return cancellationUnitPricePremium(premium_age, premium_ages[age_max], payment_method,
                                            corresponding_date_bf, cancel_date, premium_age_max_month)
                * premium_num;
    }

    //-------------------------------------------------------------------------------------------------
    //解約返戻金の算出に必要な各種掛金を求める
    //-------------------------------------------------------------------------------------------------
    private int cancellationUnitPricePremium(int premium_age, int premium, String payment_method,
                                             LocalDate corresponding_date_bf, LocalDate cancel_date,
                                             int premium_age_max_month) {
        //掛金（単価）
        int unit_price_premium = 0;

        //応当日前日～解約日の月数を求める。
        //※応当日も含めた期間を算出するため、期間算出には応当日前日を使用する。
        //　日数が1日でも経過した月は、加入期間の月とみなすため、日数を切り上げる
        Period period     = Period.between(corresponding_date_bf, cancel_date);
        int cancel_period = (int) period.toTotalMonths();
        if(period.getDays() > 0) {
            cancel_period = cancel_period + 1;
        }

        if(payment_method.equals(PAYMENT_MONTH)) {

          //月払の場合

            //掛金（単価）を算出
            unit_price_premium = (premium_age * 12) + (premium * cancel_period);
        }else {

          //年払の場合

            if(premium_age_max_month > 0) {
                //加入期間に必要な掛金（単価）を算出。応当日時点の年齢に対応する掛金は月払の掛金を用いる
                unit_price_premium  = premium_age + (premium_age_max_month * cancel_period);

            }else {
                //払込掛金（単価）を算出
                unit_price_premium  = premium_age + premium;
            }
        }

        return unit_price_premium;
    }
}
