package com.insurance.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.insurance.app.domain.WlSSearch;

@Component
public class WlSSearchValidation  implements Validator{

    final int insured_person_id_MIN = 0;
    final int insured_person_id_MAX = 2147483647;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        WlSSearch wlSSearch = (WlSSearch)target;

        //被保険者番号の数値チェック
        if(wlSSearch.getInsured_person_id_str() != "") {
            try {
                wlSSearch.setInsured_person_id_long(Long.parseLong(wlSSearch.getInsured_person_id_str()));
            }catch(NumberFormatException e) {
                errors.rejectValue("insured_person_id_str", "insured_person_id_not_number",
                        "被保険者番号は数値を入力してください");
            }
        }

        //被保険者番号の最大値チェック（int属性は最大値が「2147483647」）
        if(wlSSearch.getInsured_person_id_long() >= insured_person_id_MIN &&
           wlSSearch.getInsured_person_id_long() <= insured_person_id_MAX) {
            wlSSearch.setInsured_person_id(Math.toIntExact(wlSSearch.getInsured_person_id_long()));
        }else {
            errors.rejectValue("insured_person_id_str", "insured_person_id_min_max",
                    insured_person_id_MIN + "～" + insured_person_id_MAX + "の範囲で入力してください");
        }

    }
}
