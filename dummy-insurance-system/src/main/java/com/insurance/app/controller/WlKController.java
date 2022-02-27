package com.insurance.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insurance.app.domain.CmKWlActuarial;
import com.insurance.app.domain.WlKConfimation;
import com.insurance.app.domain.WlKInput;
import com.insurance.app.domain.WlSContract;
import com.insurance.app.domain.WlSSearch;
import com.insurance.app.service.CmKActuarialService;
import com.insurance.app.service.WlKService;
import com.insurance.app.validation.WlKInputValidation;

@Controller
@RequestMapping("/wl/k")
public class WlKController {

    @Autowired
    private CmKActuarialService cmKActuarialService;

    @Autowired
    private WlKInputValidation wlKInputValidation;

    @Autowired
    private WlKService wlKService;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(wlKInputValidation);
    }

    //F5等でブラウザをリロードしたときに２重更新されることを防ぐためのフラグ
    private boolean reloadFlg = false;

    //入力画面の表示（確認画面で戻るボタンを押下）
    @PostMapping("/input")
    public String returnInput(@ModelAttribute("wlKConfimation") WlKConfimation wlKConfimation, Model model) {

        //解約／取消　入力画面へ戻るための値を設定する
        WlKInput wlKInput = new WlKInput();
        wlKInput.setWlSContract(wlKConfimation.getWlSContract());
        wlKInput.setSelect_cancel(wlKConfimation.getSelect_cancel());
        wlKInput.setCancel_date(wlKConfimation.getCancel_date());
        model.addAttribute("wlKInput", wlKInput);
        model.addAttribute("overlay_display", "1");
        model.addAttribute("screen_info", "input");

        //契約一覧へ戻るための値を設定する
        WlSSearch wlSSearch = new WlSSearch();
        wlSSearch.setInsured_person_id_str(String.valueOf(wlKInput.getWlSContract().getInsured_person_id()));
        model.addAttribute("wlSSearch", wlSSearch);

        return "wl_s_contract";
    }

    //確認画面の表示
    @PostMapping("/confirmation")
    public String checkCancel(@ModelAttribute("wlKInput") @Validated WlKInput wlKInput, BindingResult result,
                                Model model) throws Exception{
        if (result.hasErrors()) {

            //解約／取消　入力画面へ戻るための値を設定する
            model.addAttribute("wlKInput", wlKInput);
            model.addAttribute("overlay_display", "1");
            model.addAttribute("screen_info", "input");

            //契約一覧へ戻るための値を設定する
            WlSSearch wlSSearch = new WlSSearch();
            wlSSearch.setInsured_person_id_str(String.valueOf(wlKInput.getWlSContract().getInsured_person_id()));
            model.addAttribute("wlSSearch", wlSSearch);

            return "wl_s_contract";
        }else {
            try {

                //正常な画面遷移のため、リロードによる誤更新を防ぐためのフラグにfalseを設定する
                reloadFlg = false;

                WlKConfimation wlKConfimation = new WlKConfimation();
                wlKConfimation.setWlSContract(wlKInput.getWlSContract());                           //契約照会情報
                wlKConfimation.setSelect_cancel(wlKInput.getSelect_cancel());                       //解約・取消区分

                //解約処理の場合は、解約日と解約返戻金の編集を行なう
                if(wlKInput.getSelect_cancel().equals("1")) {
                    wlKConfimation.setCancel_date(wlKInput.getCancel_date());                       //解約日
                    wlKConfimation.setCancellation_refund(cancellationRefundCalculation(wlKInput)); //解約返戻金
                }

                model.addAttribute("wlKConfimation", wlKConfimation);
                model.addAttribute("overlay_display", "1");
                model.addAttribute("screen_info", "confirmation");
                return "wl_s_contract";

            }catch(Exception e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    //更新処理
    @PostMapping("/contract")
    public String update(@ModelAttribute("wlKConfimation") WlKConfimation wlKConfimation, Model model) {
        if(reloadFlg) {
            //F5等でブラウザがリロードされたため、更新処理を行なわない
            String errorInfo =   "ブラウザのリロードによる誤更新を防ぐためにシステムエラー画面へ遷移しました";
            model.addAttribute("massage", errorInfo);
            return "cm_error";
        }else {
            try {
                //ブラウザのリロードによる誤更新を防ぐため、フラグにtrueを設定する
                reloadFlg = true;

                //解約・取消の更新処理を行なう
                String update_end_message =wlKService.update(wlKConfimation);

                //解約／取消　入力画面へ戻るための値を設定する
                WlKInput wlKInput = new WlKInput();
                wlKInput.setWlSContract(wlKConfimation.getWlSContract());
                model.addAttribute("wlKInput", wlKInput);
                model.addAttribute("overlay_display", "2");
                model.addAttribute("screen_info", "input");
                model.addAttribute("update_end_message", update_end_message);

                //契約一覧へ戻るための値を設定する
                WlSSearch wlSSearch = new WlSSearch();
                wlSSearch.setInsured_person_id_str(String.valueOf(wlKInput.getWlSContract().getInsured_person_id()));
                model.addAttribute("wlSSearch", wlSSearch);

                return "wl_s_contract";

            }catch(Exception e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }



    //数理モジュールの呼び出し
    public int cancellationRefundCalculation(WlKInput wlKInput) throws Exception{

        WlSContract wlSContract = wlKInput.getWlSContract();

        CmKWlActuarial cmKWlActuarial = new CmKWlActuarial();

        cmKWlActuarial.setInsured_person_birth_date(
                stringToDate(wlSContract.getInsured_person_birth_date(),"uuuuMMdd"));          //生年月日
        cmKWlActuarial.setInsured_person_sex(wlSContract.getInsured_person_sex());             //性別
        cmKWlActuarial.setContract_start_date(                                                 //契約開始日
                stringToDate(wlSContract.getContract_start_date(), "uuuuMMdd"));
        cmKWlActuarial.setContract_maturity_date(                                              //契約満期日
                stringToDate(wlSContract.getContract_maturity_date(), "uuuuMMdd"));
        cmKWlActuarial.setSecurity_type(wlSContract.getSecurity_type());                       //保障種類
        cmKWlActuarial.setEntry_age(wlSContract.getEntry_age());                               //加入年齢
        cmKWlActuarial.setPayment_method(wlSContract.getPayment_method());                     //払込方法
        cmKWlActuarial.setInsurance_money(wlSContract.getInsurance_money());                   //死亡保険金
        cmKWlActuarial.setPremium(wlSContract.getPremium());                                   //掛金
        cmKWlActuarial.setPremium_payment_term(wlSContract.getPremium_payment_term());         //掛金払込期間
        cmKWlActuarial.setPayment_expiration_age(wlSContract.getPayment_expiration_age());     //払込満了年齢
        cmKWlActuarial.setCancel_date(stringToDate(wlKInput.getCancel_date(),"uuuu/MM/dd"));   //解約日

        try {
            //数理モジュールを呼び出して、解約返戻金を算出する
            return  cmKActuarialService.cancellationRefundCalculation(cmKWlActuarial);

        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    //Stringの日付をLocalDate型に変換
    public LocalDate stringToDate(String str, String format) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
    }
}
