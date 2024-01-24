
$(document).ready(function(){
$('.lessthancurrdate').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	maxDate: '-0d',
	yearRange: "-100:-0",
});

});