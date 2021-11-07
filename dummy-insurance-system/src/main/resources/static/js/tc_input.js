  $(function() {
    $('#target').datepicker({
      defaultDate: new Date(2020,7,5), // 2020/8/5を表示
      numberOfMonths:2,                // 表示される月の数:2
      dateFormat: 'yy年mm月dd日',      // yyyy年mm月dd日
    });
  });