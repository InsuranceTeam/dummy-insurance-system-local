package com.insurance.app.controller;
/*
package com.insurance.app.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insurance.app.domain.WlCompletion;
import com.insurance.app.domain.WlConfirmation;
import com.insurance.app.domain.WlContracts;
import com.insurance.app.domain.WlInsuredPersons;


@Controller
@RequestMapping("/wl")
public class WlConfirmationController {

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

  //Stringの日付をLocalDate型に変換
  public LocalDate stringToDate(String str) {
      return LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
*/