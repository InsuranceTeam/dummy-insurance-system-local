package com.insurance.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insurance.app.domain.WlKInput;
import com.insurance.app.domain.WlSList;
import com.insurance.app.domain.WlSSearch;
import com.insurance.app.service.WlSService;
import com.insurance.app.validation.WlSSearchValidation;

@Controller
@RequestMapping("/wl/s")
public class WlSController {

    @Autowired
    private WlSService wlSService;

    @Autowired
    private WlSSearchValidation wlSSearchValidation;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(wlSSearchValidation);
    }

    //被保険者検索画面の表示
    @GetMapping("/search")
    public String wlSSearch(@ModelAttribute("wlSSearch") WlSSearch wlSSearch, Model model) {
        model.addAttribute("wlSSearch", wlSSearch);
        return "wl_s_search";
    }

    //一覧画面の表示
    @PostMapping("/list")
    public String wlSList(@ModelAttribute("wlSSearch") @Validated WlSSearch wlSSearch, BindingResult result,
                        Model model) throws Exception{
        if (result.hasErrors()) {
            //バリデーションエラー
            return "wl_s_search";
        }else {
            try {
                //終身生命契約を取得する
                List<WlSList> wlSLists = wlSService.searchWlSList(wlSSearch.getInsured_person_id());

                if(wlSLists.size() == 0) {
                    //終身生命契約なしのため、その旨をメッセージ出力する
                    result.rejectValue("insured_person_id_str", "WL_nothing",
                            "終身生命契約が存在しません");
                    return "wl_s_search";
                }else {
                    //一覧画面へ遷移
                    model.addAttribute("wlSSearch", wlSSearch);
                    model.addAttribute("wlSLists", wlSLists);
                    return "wl_s_list";
                }

            }catch(Exception e) {
                //テーブル取得で予期せぬ処理が発生
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    //契約照会の表示
    @GetMapping("/contract/{contract_key}")
    public String wlSContract(@PathVariable String contract_key, Model model) throws Exception{

        try {

            //contract_keyを「&」で分割する（0:被保険者番号、1:契約番号）
            String[] contract_keys = contract_key.split("&");
            int insured_person_id = Integer.parseInt(contract_keys[0]);
            int contract_id       = Integer.parseInt(contract_keys[1]);

            WlKInput wlKInput = new WlKInput();

            //照会用の契約情報を取得する（現在は１契約１履歴なので、先頭の履歴のみ取得する）
            wlKInput.setWlSContract(wlSService.getWlSContract(insured_person_id, contract_id).get(0));

            //解約・取消入力画面用のエリアを設定
            model.addAttribute("wlKInput", wlKInput);
            model.addAttribute("overlay_display", "0");
            model.addAttribute("screen_info", "input");

            //契約一覧へ戻るための値を設定する
            WlSSearch wlSSearch = new WlSSearch();
            wlSSearch.setInsured_person_id_str(String.valueOf(insured_person_id));
            model.addAttribute("wlSSearch", wlSSearch);

            return "wl_s_contract";

        }catch(Exception e) {
            //テーブル取得で予期せぬ処理が発生
            model.addAttribute("massage", e.getMessage());
            return "cm_error";
        }
    }

}
