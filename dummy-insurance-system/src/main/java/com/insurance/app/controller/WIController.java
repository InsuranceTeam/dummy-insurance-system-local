package com.insurance.app.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.icu.text.Transliterator;
import com.insurance.app.domain.CmWlActuarial;
import com.insurance.app.domain.WlCompletion;
import com.insurance.app.domain.WlConfirmation;
import com.insurance.app.domain.WlContracts;
import com.insurance.app.domain.WlInput;
import com.insurance.app.domain.WlInsuredPersons;
import com.insurance.app.service.CmActuarialService;
import com.insurance.app.service.WlService;
import com.insurance.app.validation.WlInputValidation;

@Controller
@RequestMapping("/wl")
public class WIController {

    @Autowired
    private WlInputValidation wlInputValidation;

    @Autowired
    private CmActuarialService cmActuarialService;

    @Autowired
    private WlService wlService;

    //F5等でブラウザをリロードしたときに２重更新されることを防ぐためのフラグ
    private boolean reloadFlg = false;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(wlInputValidation);
    }

    //入力画面の表示
    @GetMapping("/input")
    public String wlInput(@ModelAttribute("wlInput") WlInput wlInput, Model model) {
        model.addAttribute("wlInput", wlInput);
        return "wl_input";
    }

    //確認画面の表示
    @PostMapping("/confirmation")
    public String check(@ModelAttribute("wlInput") @Validated WlInput wlInput, BindingResult result, Model model)
                        throws Exception{
        if (result.hasErrors()) {
            return "wl_input";
        }else {
            try {
                model.addAttribute("wlInput", wlInput);

                //入力情報の編集
                WlConfirmation wlConfimation = new WlConfirmation();
                wlConfimation.setWlInput(wlInput);

                //加入年齢の編集
                LocalDate birth_date = stringToDate(wlInput.getInsured_person_birth_date());
                LocalDate start_date = stringToDate(wlInput.getStart_date());
                wlConfimation.setEntry_age(Period.between(birth_date, start_date).getYears());

                //掛金の編集
                wlConfimation.setPremium(premiumCalculation(wlInput));

                //確認画面へ入力情報、加入年齢、掛金を引き継ぐ
                model.addAttribute("wlConfimation", wlConfimation);

                //正常な画面遷移のため、リロードによる誤更新を防ぐためのフラグにfalseを設定する
                reloadFlg = false;

                return "wl_confirmation";

            }catch(Exception e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    //完了画面の表示
    @PostMapping("/completion")
    public String entry(@ModelAttribute("wlConfirmation") WlConfirmation wlConfirmation, Model model)
                        throws Exception{
        if(reloadFlg) {
            //F5等でブラウザがリロードされたため、更新処理を行なわない
            String errorInfo =   "ブラウザのリロードによる誤更新を防ぐためにシステムエラー画面へ遷移しました";
            model.addAttribute("massage", errorInfo);
            return "cm_error";
        }else {
            try {
                //ブラウザのリロードによる誤更新を防ぐため、フラグにtrueを設定する
                reloadFlg = true;

                //被保険者情報の編集を行なう
                WlInsuredPersons wlInsuredPersons = new WlInsuredPersons();
                editInsuredPersons(wlConfirmation, wlInsuredPersons);

                //契約情報の編集を行なう
                WlContracts wlContracts = new WlContracts();
                editContracts(wlConfirmation, wlInsuredPersons, wlContracts);

                //被保険者情報の登録／更新 及び 契約情報の登録を行なう
                String iuComment = wlService.entryContracts(wlInsuredPersons, wlContracts);


                //完了画面に表示する値を編集する（被保険者氏名（カナ）は全角、日付は"yyyy/mm/dd"形式）
                wlInsuredPersons.setInsured_person_name_kana(
                          wlConfirmation.getInsured_person_name_kana_surname() + "　"
                        + wlConfirmation.getInsured_person_name_kana_name());
                wlInsuredPersons.setInsured_person_birth_date(wlConfirmation.getInsured_person_birth_date());
                wlContracts.setContract_start_date(wlConfirmation.getStart_date());
                wlContracts.setContract_end_date("0000/00/00");
                wlContracts.setContract_maturity_date("9999/12/31");

                WlCompletion wlCompletion = new WlCompletion();
                wlCompletion.setWlInsuredPersons(wlInsuredPersons);
                wlCompletion.setWlContracts(wlContracts);
                model.addAttribute("wlCompletion", wlCompletion);

                //被保険者情報テーブルが登録／更新のどちらであるかを画面表示する
                model.addAttribute("iuComment", iuComment);

                return "wl_completion";

            }catch(Exception e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    //数理モジュールの呼び出し
    public int premiumCalculation(WlInput wlInput) throws Exception{
        CmWlActuarial cmWlActuarial = new CmWlActuarial();

        //保障区分
        cmWlActuarial.setSecurity_type("WL");

        //加入時年齢
        LocalDate birth_date = stringToDate(wlInput.getInsured_person_birth_date());
        LocalDate start_date = stringToDate(wlInput.getStart_date());
        cmWlActuarial.setEntry_age(Period.between(birth_date, start_date).getYears());

        //性別
        cmWlActuarial.setInsured_person_sex(wlInput.getInsured_person_sex());

        //払込方法
        cmWlActuarial.setPayment_method(wlInput.getPayment_method());

        //払込満了年齢
        cmWlActuarial.setPayment_expiration_age(wlInput.getPayment_expiration_age());

        //加入日
        cmWlActuarial.setReference_date(LocalDate.parse(wlInput.getStart_date(),DateTimeFormatter.ofPattern("uuuu/MM/dd")));

        //死亡保険金
        cmWlActuarial.setInsurance_money(wlInput.getInsurance_money());

        try {
            return  cmActuarialService.findPremium(cmWlActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //被保険者情報の登録／更新
    public void editInsuredPersons(WlConfirmation   wlConfirmation,
                                    WlInsuredPersons wlInsuredPersons) throws Exception {
        try {

            //被保険者氏名（漢字）
            if(wlConfirmation.getInsured_person_name_kanji_surname() != "") {
                wlInsuredPersons.setInsured_person_name_kanji(wlConfirmation.getInsured_person_name_kanji_surname() + "　"
                                                              + wlConfirmation.getInsured_person_name_kanji_name());
            }else {
                wlInsuredPersons.setInsured_person_name_kanji("");
            }

            //被保険者氏名（カナ）⇒ 被保険者氏名(ｶﾅ)
            String name_kana_em = wlConfirmation.getInsured_person_name_kana_surname() + "　"
                    + wlConfirmation.getInsured_person_name_kana_name();
            Transliterator transliterator = Transliterator.getInstance("Fullwidth-Halfwidth");
            String name_kana    = transliterator.transliterate(name_kana_em);
            wlInsuredPersons.setInsured_person_name_kana(name_kana);

            //生年月日
            String birth_date = wlConfirmation.getInsured_person_birth_date().replace("/", "");
            wlInsuredPersons.setInsured_person_birth_date(birth_date);

            //性別
            String sex = wlConfirmation.getInsured_person_sex();
            wlInsuredPersons.setInsured_person_sex(sex);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //契約情報の登録
    public void editContracts(WlConfirmation   wlConfirmation,
                               WlInsuredPersons wlInsuredPersons,
                               WlContracts      wlContracts) throws Exception {
        try {

            //契約履歴番号
            wlContracts.setContract_history_id(10000);

            //契約開始日
            String start_date = wlConfirmation.getStart_date().replace("/", "");
            wlContracts.setContract_start_date(start_date);

            //契約終了日
            wlContracts.setContract_end_date("00000000");
            //契約終了事由
            wlContracts.setContract_end_reason("");
            //契約満期日
            wlContracts.setContract_maturity_date("99991231");
            //保障種類
            wlContracts.setSecurity_type("WL");

            //加入年齢
            int entry_age = wlConfirmation.getEntry_age();
            wlContracts.setEntry_age(entry_age);

            //払込方法
            wlContracts.setPayment_method(wlConfirmation.getPayment_method());
            //死亡保険金
            wlContracts.setInsurance_money(wlConfirmation.getInsurance_money());
            //掛金
            wlContracts.setPremium(wlConfirmation.getPremium());

            //掛金払込期間
            int premium_payment_term = wlConfirmation.getPayment_expiration_age() == 99 ? 99 : 60 - entry_age;
            wlContracts.setPremium_payment_term(premium_payment_term);

            //契約期間
            wlContracts.setContract_term(99);
            //払込満了年齢
            wlContracts.setPayment_expiration_age(wlConfirmation.getPayment_expiration_age());

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //Stringの日付をLocalDate型に変換
    public LocalDate stringToDate(String str) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern("uuuu/MM/dd"));
    }
}
