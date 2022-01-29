package com.insurance.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insurance.app.domain.CmTcActuarial;
import com.insurance.app.domain.CmTlActuarial;
import com.insurance.app.domain.CmWcActuarial;
import com.insurance.app.service.CmActuarialService;



@Controller
@RequestMapping("/cm/test")
public class ActuarialTestController {

    @Autowired
    private CmActuarialService cmActuarialService;


    @GetMapping
    public String test(Model model) throws Exception {
        try {
            String[] security_types = {"TC","TL","WC","WL"};
            String[] entry_ages_t      = {"00","70"};
            String[] entry_ages_wc_60  = {"15","55"};
            String[] entry_ages_wc_99  = {"65"};
            //String[] entry_ages_wl_60  = {"0","55"};
            //String[] entry_ages_wl_99  = {"0","65"};
            String[] insured_person_sexs = {"1", "2"};
            String[] payment_methods     = {"1", "3"};
            String[] payment_expiration_ages = {"60","99"};
            String[] premium_payment_terms = {"05","10"};
            String   hospitalization_money   = "2000";
            String   insurance_money         = "0200";
            StringBuilder result = new StringBuilder();

            Map<String, String> map = new HashMap<>();

            //定期医療
            for (String entry_age : entry_ages_t) {
                for (String insured_person_sex : insured_person_sexs) {
                    for (String payment_method : payment_methods) {
                        map.put("security_type",           security_types[0]);
                        map.put("entry_age",               entry_age);
                        map.put("insured_person_sex",      insured_person_sex);
                        map.put("payment_method",          payment_method);
                        map.put("reference_date",          "2021-01-01");
                        map.put("hospitalization_money",   hospitalization_money);

                        //払込期間5年
                        map.put("premium_payment_term",  premium_payment_terms[0]);
                        result.append(security_types[0]           + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      premium_payment_terms[0]    + " | " +
                                      hospitalization_money       + " | " +
                                      Integer.toString(premiumTcCalculation(map)) + ",");

                        //払込期間10年
                        map.put("premium_payment_term",  premium_payment_terms[1]);
                        result.append(security_types[0]     + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      premium_payment_terms[1]    + " | " +
                                      hospitalization_money       + " | " +
                                      Integer.toString(premiumTcCalculation(map)) + ",");
                    }
                }

            }

            //定期生命
            for (String entry_age : entry_ages_t) {
                for (String insured_person_sex : insured_person_sexs) {
                    for (String payment_method : payment_methods) {
                        map.put("security_type",           security_types[1]);
                        map.put("entry_age",               entry_age);
                        map.put("insured_person_sex",      insured_person_sex);
                        map.put("payment_method",          payment_method);
                        map.put("reference_date",          "2021-01-01");
                        map.put("insurance_money",         insurance_money);

                        //払込期間5年
                        map.put("premium_payment_term",  premium_payment_terms[0]);
                        result.append(security_types[1]           + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      premium_payment_terms[0]    + " | " +
                                      insurance_money             + " | " +
                                      Integer.toString(premiumTlCalculation(map)) + ",");

                        //払込期間10年
                        map.put("premium_payment_term",  premium_payment_terms[1]);
                        result.append(security_types[1]     + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      premium_payment_terms[1]    + " | " +
                                      insurance_money             + " | " +
                                      Integer.toString(premiumTlCalculation(map)) + ",");
                    }
                }

            }

            //終身医療（６０歳満了）
            for (String entry_age : entry_ages_wc_60) {
                for (String insured_person_sex : insured_person_sexs) {
                    for (String payment_method : payment_methods) {
                        map.put("security_type",           security_types[2]);
                        map.put("entry_age",               entry_age);
                        map.put("insured_person_sex",      insured_person_sex);
                        map.put("payment_method",          payment_method);
                        map.put("reference_date",          "2021-01-01");
                        map.put("hospitalization_money",   hospitalization_money);
                        map.put("payment_expiration_age",  payment_expiration_ages[0]);

                        result.append(security_types[2]           + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      payment_expiration_ages[0]  + " | " +
                                      hospitalization_money       + " | " +
                                      Integer.toString(premiumWcCalculation(map)) + ",");
                    }
                }

            }

            //終身医療（終身）
            for (String entry_age : entry_ages_wc_99) {
                for (String insured_person_sex : insured_person_sexs) {
                    for (String payment_method : payment_methods) {
                        map.put("security_type",           security_types[2]);
                        map.put("entry_age",               entry_age);
                        map.put("insured_person_sex",      insured_person_sex);
                        map.put("payment_method",          payment_method);
                        map.put("reference_date",          "2021-01-01");
                        map.put("hospitalization_money",   hospitalization_money);

                        map.put("payment_expiration_age",  payment_expiration_ages[1]);
                        result.append(security_types[2]           + " | " +
                                      entry_age                   + " | " +
                                      insured_person_sex          + " | " +
                                      payment_method              + " | " +
                                      payment_expiration_ages[1]  + " | " +
                                      hospitalization_money       + " | " +
                                      Integer.toString(premiumWcCalculation(map)) + ",");
                    }
                }

            }

            model.addAttribute("massage", result);
            return "cm_error";
        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //数理モジュールの呼び出し（定期医療）
    public int premiumTcCalculation(Map<String, String> map) throws Exception{
        CmTcActuarial CmTcActuarial = new CmTcActuarial();
        CmTcActuarial.setSecurity_type(map.get("security_type"));             //保障区分
        CmTcActuarial.setEntry_age(Integer.parseInt(map.get("entry_age")));  //加入時年齢
        CmTcActuarial.setInsured_person_sex(map.get("insured_person_sex"));   //性別
        CmTcActuarial.setPayment_method(map.get("payment_method"));           //払込方法
        CmTcActuarial.setPremium_payment_term(
                      Integer.parseInt(map.get("premium_payment_term")));  //掛金払込期間
        CmTcActuarial.setReference_date(
                        LocalDate.parse(map.get("reference_date"),
                              DateTimeFormatter.ofPattern("yyyy-MM-dd")));   //加入日
        CmTcActuarial.setHospitalization_money(
                        Integer.parseInt(map.get("hospitalization_money"))); //入院日額
        try {
            return  cmActuarialService.findPremium(CmTcActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //数理モジュールの呼び出し（定期生命）
    public int premiumTlCalculation(Map<String, String> map) throws Exception{
        CmTlActuarial CmTlActuarial = new CmTlActuarial();
        CmTlActuarial.setSecurity_type(map.get("security_type"));             //保障区分
        CmTlActuarial.setEntry_age(Integer.parseInt(map.get("entry_age")));  //加入時年齢
        CmTlActuarial.setInsured_person_sex(map.get("insured_person_sex"));   //性別
        CmTlActuarial.setPayment_method(map.get("payment_method"));           //払込方法
        CmTlActuarial.setPremium_payment_term(
                      Integer.parseInt(map.get("premium_payment_term")));  //掛金払込期間
        CmTlActuarial.setReference_date(
                        LocalDate.parse(map.get("reference_date"),
                              DateTimeFormatter.ofPattern("yyyy-MM-dd")));   //加入日
        CmTlActuarial.setInsurance_money(
                        Integer.parseInt(map.get("insurance_money")));       //死亡保険金
        try {
            return  cmActuarialService.findPremium(CmTlActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //数理モジュールの呼び出し（終身医療）
    public int premiumWcCalculation(Map<String, String> map) throws Exception{
        CmWcActuarial CmWcActuarial = new CmWcActuarial();
        CmWcActuarial.setSecurity_type(map.get("security_type"));             //保障区分
        CmWcActuarial.setEntry_age(Integer.parseInt(map.get("entry_age")));  //加入時年齢
        CmWcActuarial.setInsured_person_sex(map.get("insured_person_sex"));   //性別
        CmWcActuarial.setPayment_method(map.get("payment_method"));           //払込方法
        CmWcActuarial.setPayment_expiration_age(
                      Integer.parseInt(map.get("payment_expiration_age")));  //払込満了年齢
        CmWcActuarial.setReference_date(
                        LocalDate.parse(map.get("reference_date"),
                              DateTimeFormatter.ofPattern("yyyy-MM-dd")));   //加入日
        CmWcActuarial.setHospitalization_money(
                        Integer.parseInt(map.get("hospitalization_money"))); //入院日額
        try {
            return  cmActuarialService.findPremium(CmWcActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }
}
