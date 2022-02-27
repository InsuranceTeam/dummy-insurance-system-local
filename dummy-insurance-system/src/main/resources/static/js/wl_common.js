$(function(){

  //解約日（指定可能期間は契約開始日～120歳時点の満期日）
  $('.datepicker_cancel').datepicker({
    defaultDate: new Date($("#contract_start_date").html()), // 契約開始日を初期設定
    changeYear: true,    // 年を表示
    changeMonth: true,   // 月を選択
    dateFormat: 'yy/mm/dd',      // yyyy/mm/dd
    minDate: new Date($("#contract_start_date").html()), //契約開始日より前日付は指定不可
    maxDate: getEndDate($("#contract_start_date").html(), $("#entry_age").html()), //120歳時点の満期日を終了日としたい
    yearRange:"0:+" + String(120 - Number($("#entry_age").html().replace("　歳",""))) //120歳までを入力範囲とする
  });

//global nav
 var btn = $(".mod_dropnavi ul li.parent");
 var submenu = $(".mod_dropnavi_child");
 var submenulink = $(".mod_dropnavi_child ul li a");
    //click
    $(btn).bind("click", "focus", function(event){
     var shownav = $(this).find(".mod_dropnavi_child");
    if($(shownav).css("display")=="none"){
        $(shownav).slideDown("fast");
    }else{
        $(shownav).slideUp("fast");
   }
 });

  //hover
    $(btn).hover(function () {
     },
      function () {
        $(submenu).slideUp("fast");
  });

  //モーダルウィンドウの表示・表示制御（初期表示）
  switch($("#overlay_display").val()){
    case '1':
      $("body").addClass("no_scroll"); // 背景固定させるクラス付与
      $('#overlay, .modal-window').show();
      break;
    case '2':
      $("#update_end").dialog({
        modal:true,
        title:"完了メッセージ",
        buttons: {"OK": function() {$(this).dialog("close");}}
      });
      break;
  }
  if($("#overlay_display").val() == '1'){
    $("body").addClass("no_scroll"); // 背景固定させるクラス付与
    $('#overlay, .modal-window').show();
  }

  //モーダルウィンドウの表示・表示制御（解約・取消ボタン押下時）
  $('.js-open').click(function () {
    $("body").addClass("no_scroll"); // 背景固定させるクラス付与
    $("#select-cancel").val("1"); //「解約・取消　区分」を「解約」に設定
    $(".Date-box-show").show();   //解約日の入力項目を表示する
    $(".text-danger").html('<div class="text-danger"></div>');  //エラーメッセージをクリア
    $("#datepicker_cancel").val("");  //解約日をクリア
    $('#overlay, .modal-window').fadeIn();
  });
  $('.js-close').click(function () {
    $("body").removeClass("no_scroll"); // 背景固定させるクラス削除
    $('#overlay, .modal-window').fadeOut();
  });

  //解約日の表示・非表示　切替（初期表示）
  if($("#screen_info").val() == 'input' && $("#select-cancel").val() == '2'){
    $(".text-danger").html('<div class="text-danger"></div>'); //エラーメッセージをクリア
    $("#datepicker_cancel").val("");  //解約日をクリア
    $(".Date-box-show").hide();       //解約日の入力項目を非表示にする
  };

  //解約日の表示・非表示　切替（「解約・取消　区分」が変更されたとき）
  $("#select-cancel").change(function(){
    $(".text-danger").html('<div class="text-danger"></div>'); //エラーメッセージをクリア
    var select_cancel = $(this).val();
    if(select_cancel == '1'){
      $(".Date-box-show").show();       //解約日の入力項目を表示する
    }else{
      $("#datepicker_cancel").val("");  //解約日をクリア
      $(".Date-box-show").hide();       //解約日の入力項目を非表示にする
    }
  });

/*
  $("#btn").click(function() {
    $("#dl").dialog({
      modal:true,
      title:"ダイアログのタイトル",
      buttons: {"OK": function() {$(this).dialog("close");}}
    });
  });
*/
});

function getEndDate(contract_start_date_str, entry_age_str){
  var contract_start_date = new Date(contract_start_date_str) ;
  contract_start_date.setDate(contract_start_date.getDate() - 1); //契約開始日の前日を求める
  var entry_age  = Number(entry_age_str.replace("　歳",""));
  var yearNum    = contract_start_date.getFullYear() + (120 - entry_age); //120歳時点の年を求める
  var monthNum   = contract_start_date.getMonth() + 1;                    //1ヵ月小さい値が帰ってくるので1加算する
  var dayNum     = contract_start_date.getDate();
  var endDateStr =   String( yearNum ) + "/"
                   + String( monthNum ) + "/"
                   + String( dayNum ) ;
  return new Date(endDateStr);
}