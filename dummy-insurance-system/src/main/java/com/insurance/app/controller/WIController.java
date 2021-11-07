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

import com.insurance.app.domain.CmWlActuarial;
import com.insurance.app.domain.WlCompletion;
import com.insurance.app.domain.WlConfirmation;
import com.insurance.app.domain.WlContracts;
import com.insurance.app.domain.WlInput;
import com.insurance.app.domain.WlInsuredPersons;
import com.insurance.app.service.CmActuarialService;
import com.insurance.app.validation.WlInputValidation;

@Controller
@RequestMapping("/wl")
public class WIController {

    @Autowired
    private WlInputValidation wlInputValidation;

    @Autowired
    private CmActuarialService cmActuarialService;

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

                model.addAttribute("wlConfimation", wlConfimation);

                return "wl_confirmation";

            }catch(Exception e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    //完了画面の表示
    @PostMapping("/completion")
    public String entry(@ModelAttribute("wlConfirmation") WlConfirmation wlConfirmation, Model model) {
        WlCompletion wlCompletion = new WlCompletion();

        WlInsuredPersons wlInsuredPersons = new WlInsuredPersons();
        WlContracts wlContracts = new WlContracts();

        LocalDate birth_date = stringToDate(wlConfirmation.getInsured_person_birth_date());
        LocalDate start_date = stringToDate(wlConfirmation.getStart_date());
        int entry_age  = Period.between(birth_date, start_date).getYears();
        int premium_payment_term = wlConfirmation.getPayment_expiration_age() == 99 ? 99 : 60 - entry_age;

        wlInsuredPersons.setInsured_person_id(1000000000);
        wlInsuredPersons.setInsured_person_name_kanji(wlConfirmation.getInsured_person_name_kanji_surname() + "　"
                                                      + wlConfirmation.getInsured_person_name_kanji_name());
        wlInsuredPersons.setInsured_person_name_kana(wlConfirmation.getInsured_person_name_kana_surname() + "　"
                                                      + wlConfirmation.getInsured_person_name_kana_name());
        wlInsuredPersons.setInsured_person_birth_date(birth_date);
        wlInsuredPersons.setInsured_person_sex(wlConfirmation.getInsured_person_sex());

        wlContracts.setContract_id(10000);
        wlContracts.setContract_history_id(10001);
        wlContracts.setContract_start_date(start_date);
        wlContracts.setContract_end_date(stringToDate("9999/12/31"));
        wlContracts.setContract_end_reason("");
        wlContracts.setContract_maturity_date(stringToDate("9999/12/31"));
        wlContracts.setSecurity_type("WL");
        wlContracts.setEntry_age(entry_age);
        wlContracts.setPayment_method(wlConfirmation.getPayment_method());
        wlContracts.setInsurance_money(wlConfirmation.getInsurance_money());
        wlContracts.setPremium(wlConfirmation.getPremium());
        wlContracts.setPremium_payment_term(premium_payment_term);
        wlContracts.setContract_term(99);
        wlContracts.setPayment_expiration_age(wlConfirmation.getPayment_expiration_age());

        wlCompletion.setWlInsuredPersons(wlInsuredPersons);
        wlCompletion.setWlContracts(wlContracts);
        model.addAttribute("wlCompletion", wlCompletion);

        return "wl_completion";
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
        cmWlActuarial.setReference_date(LocalDate.parse(wlInput.getStart_date(),DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        //死亡保険金
        cmWlActuarial.setInsurance_money(wlInput.getInsurance_money());

        try {
            return  cmActuarialService.findPremium(cmWlActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //Stringの日付をLocalDate型に変換
    public LocalDate stringToDate(String str) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
