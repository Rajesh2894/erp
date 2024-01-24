/**
 * Lalit Mohan
 */
$(function () {
    $("#fromDate").datepicker({
        numberOfMonths: 1,
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        yearRange : "-100:-0",
        maxDate : '-0d',
        onSelect: function (selected) {
          
            $("#toDate").datepicker("option", "minDate", selected);
        }
    });
    $("#toDate").datepicker({
        numberOfMonths: 1,
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        yearRange : "-100:-0",
        maxDate : '-0d',
        onSelect: function (selected) {
        	
            $("#fromDate").datepicker("option", "maxDate", selected);
        }
    });
    $('#fromDate').val( $('#fromDate').val().substring(0,10));	
    $('#toDate').val( $('#toDate').val().substring(0,10));	
});
function clearForm(url) {
	window.open(url, '_self', false);

}