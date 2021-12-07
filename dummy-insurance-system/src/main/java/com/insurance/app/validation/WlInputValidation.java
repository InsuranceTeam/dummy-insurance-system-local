package com.insurance.app.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.insurance.app.domain.WlInput;

@Component
public class WlInputValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        WlInput wlInput = (WlInput)target;

        //被保険者氏名（漢字）のバリデーション
        validateKanjiName(wlInput.getInsured_person_name_kanji_surname(),
                          wlInput.getInsured_person_name_kanji_name(),
                          errors);

        //被保険者氏名（カナ）のバリデーション
        validateKanaName(wlInput.getInsured_person_name_kana_surname(),
                         wlInput.getInsured_person_name_kana_name(),
                         errors);

        //生年月日の暦日チェック
        String birth_date_str = wlInput.getInsured_person_birth_date();
        boolean birth_date_check_flg = true;

        if(!birth_date_str.isEmpty()) {
            if(calendarDayCheck(birth_date_str)) {
            }else {
                errors.rejectValue("insured_person_birth_date", "birth_date_calendar_err",
                        "暦日を入力してください");
                birth_date_check_flg = false;
            }
        }else {
            birth_date_check_flg = false;
        }

        //加入日の暦日チェック
        String start_date_str = wlInput.getStart_date();
        boolean start_date_check_flg = true;

        if(!start_date_str.isEmpty()) {
            if(calendarDayCheck(start_date_str)) {
            }else {
                errors.rejectValue("start_date", "start_date_calendar_err",
                        "暦日を入力してください");
                start_date_check_flg = false;
            }
        }else {
            start_date_check_flg = false;
        }

        //生年月日と加入日の比較チェック、加入時年齢チェック
        if(birth_date_check_flg && start_date_check_flg){
            validateDate(LocalDate.parse(birth_date_str, DateTimeFormatter.ofPattern("uuuu/MM/dd")),
                         LocalDate.parse(start_date_str, DateTimeFormatter.ofPattern("uuuu/MM/dd")),
                         wlInput.getPayment_expiration_age(),
                         errors);
        }
    }

    //被保険者氏名（漢字）チェック
    public void validateKanjiName(String kanji_surname, String kanji_name, Errors errors) {

        //被保険者氏名（漢字）　姓・名の入力整合性チェック
        if((kanji_surname.isEmpty() && !kanji_name.isEmpty()) ||
           (!kanji_surname.isEmpty() && kanji_name.isEmpty())){
            errors.rejectValue("insured_person_name_kanji_surname", "name_kanji",
                    "姓・名は両方入力　もしくは　両方未入力としてください");
        }

        //被保険者氏名（漢字）の空白文字チェック
        if(blankCheck(kanji_surname) && blankCheck(kanji_name)) {
        }else {
            errors.rejectValue("insured_person_name_kanji_surname", "name_kanji_blank",
                    "空白文字（全角、半角）は入力しないでください");
        };
    }

    //被保険者氏名（カナ）チェック
    public void validateKanaName(String kana_surname, String kana_name, Errors errors) {

        //被保険者氏名（カナ）の空白文字チェック
        if(blankCheck(kana_surname) && blankCheck(kana_name)) {
        }else {
            errors.rejectValue("insured_person_name_kana_surname", "name_kana_blank",
                    "空白文字（全角、半角）は入力しないでください");
        };
    }

    //生年月日と加入日比較チェック、加入時年齢チェック
    public void validateDate(LocalDate birth_date, LocalDate start_date,
                             int payment_expiration_age, Errors errors) {
        //生年月日と加入日の比較チェック
        if(start_date.isBefore(birth_date)) {
            errors.rejectValue("start_date", "birth_date>start_date",
                    "生年月日と同日、または生年月日より先の日付を入力してください");
        }else {
            //加入時年齢チェック
            int entry_age  = Period.between(birth_date, start_date).getYears();
            if(payment_expiration_age == 99) {
                //終身払時の加入時年齢チェック
                if(entry_age > 65) {
                    errors.rejectValue("start_date", "entry_age_over_99",
                            "終身払の上限年齢６５歳を超えています");
                }
            }else {
                //６０歳払込満了時の加入時年齢チェック
                if(entry_age > 55) {
                    errors.rejectValue("start_date", "entry_age_over_60",
                            "６０歳払込満了の上限年齢５５歳を超えています");
                }
            }
        }
    }

    //空白入力チェック
    public Boolean blankCheck(String str) {
        String str_trim = str.replaceAll("[\\h]+", "");
        if(str.equals(str_trim)) {
            return true;
        }else {
            return false;
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
