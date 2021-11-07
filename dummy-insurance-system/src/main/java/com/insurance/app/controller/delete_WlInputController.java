package com.insurance.app.controller;
/*
package com.insurance.app.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.apache.ibatis.binding.BindingException;
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
import com.insurance.app.domain.WlConfirmation;
import com.insurance.app.domain.WlInput;
import com.insurance.app.service.CmActuarialService;
import com.insurance.app.validation.WlInputValidation;

@Controller
@RequestMapping("/wl")
public class WlInputController{

    @Autowired
    private WlInputValidation wlInputValidation;

    @Autowired
    private CmActuarialService cmActuarialService;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(wlInputValidation);
    }

    @GetMapping("/input")
    public String wlInput(@ModelAttribute("wlInput") WlInput wlInput, Model model) {
        model.addAttribute("wlInput", wlInput);
        return "wl_input";
    }

    @PostMapping("/confirmation")
    public String check(@ModelAttribute("wlInput") @Validated WlInput wlInput, BindingResult result, Model model)
    throws BindingException{
        if (result.hasErrors()) {
            return "wl_input";
        }else {
            try {
                model.addAttribute("wlInput", wlInput);

                WlConfirmation wlConfimation = new WlConfirmation();
                wlConfimation.setWlInput(wlInput);
                    wlConfimation.setPremium(premiumCalculation(wlInput));

                model.addAttribute("wlConfimation", wlConfimation);

                return "wl_confirmation";

            }catch(BindingException e) {
                model.addAttribute("massage", e.getMessage());
                return "cm_error";
            }
        }
    }

    public int premiumCalculation(WlInput wlInput) throws BindingException{
        CmWlActuarial cmWlActuarial = new CmWlActuarial();

        //保障区分
        cmWlActuarial.setSecurity_type("WL");

        //加入時年齢
        LocalDate birth_date = LocalDate.parse(wlInput.getInsured_person_birth_date(),
                                                DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        LocalDate start_date = LocalDate.parse(wlInput.getStart_date(),
                                                DateTimeFormatter.ofPattern("yyyy/MM/dd"));
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
        }catch(BindingException e)  {
            throw new BindingException(e);
        }
    }
}
*/