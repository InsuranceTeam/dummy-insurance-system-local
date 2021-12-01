$(function() {
  $('.datepicker_birth').datepicker({
    defaultDate: new Date(1990 ,1-1,1), // 1990/1/1を初期設定
    changeYear: true,    // 年を表示
    changeMonth: true,   // 月を選択
    dateFormat: 'yy/mm/dd',      // yyyy/mm/dd
    yearRange: "-120:+0"
  });

  $('.datepicker_start').datepicker({
    defaultDate: new Date(), // 処理日を初期設定
    changeYear: true,    // 年を表示
    changeMonth: true,   // 月を選択
    dateFormat: 'yy/mm/dd',      // yyyy/mm/dd
    yearRange: "-10:+10"
  });
});