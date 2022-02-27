package com.insurance.app.domain;

import lombok.Data;

@Data
public class WlKConfimation {
    private WlSContract wlSContract;         //照会画面情報
    private String      select_cancel;       //解約・取消区分
    private String      cancel_date;         //解約日
    private int         cancellation_refund; //解約返戻金
}
