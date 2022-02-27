package com.insurance.app.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.insurance.app.domain.WlKInput;

@Component
public class WlKInputValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        WlKInput wlKInput = (WlKInput)target;

        //解約処理に対するチェック
        if(wlKInput.getSelect_cancel().equals("1")) {

            //解約日の暦日チェック
            String cancel_date_str = wlKInput.getCancel_date();
            boolean cancel_date_check_flg = false;

            if(!cancel_date_str.isEmpty()) {
                if(calendarDayCheck(cancel_date_str)) {
                    cancel_date_check_flg = true;
                }else {
                    errors.rejectValue("cancel_date", "cancel_date_calendar_err",
                            "暦日を入力してください");
                }
            }else {
                errors.rejectValue("cancel_date", "cancel_date_blank_err",
                        "解約日を入力してください");
            }

            //解約日時点の契約有効チェック
            if(cancel_date_check_flg){
                LocalDate cancel_date = LocalDate.parse(cancel_date_str, DateTimeFormatter.ofPattern("uuuu/MM/dd"));
                LocalDate start_date  = LocalDate.parse(wlKInput.getWlSContract().getContract_start_date(),
                                                        DateTimeFormatter.ofPattern("uuuuMMdd"));

                if(cancel_date.isBefore(start_date)) {
                    errors.rejectValue("cancel_date", "cancel_date<start_date",
                            "契約発効日以降を");
                    errors.rejectValue("cancel_date", "cancel_date<start_date",
                            "入力してください");
                }
            }
        }
    }

    //暦日チェック
    public Boolean calendarDayCheck(String date_str) {
        try {
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT).
            parse(date_str, LocalDate::from);
            return true;

        } catch (Exception e) {
            return false;}

    }
}
