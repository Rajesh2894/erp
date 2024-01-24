/**
 * Lalit Mohan
 */
$(function() {
		$("#fromDate").datepicker({
			maxDate : '-0d',
			changeMonth : true,
			changeYear : true,
			yearRange : "-100:-0",
		});
	});
	function clearForm(url) {
		window.open(url, '_self', false);

	}

	function saveForm(element) {
		return saveOrUpdateForm(element, $('#resubmitDoc').val(),'DocumentResubmission.html', 'saveform');

	}
	function resubmitForm(element) {
		return saveOrUpdateForm(element, $('#resubmitDoc').val(),'AdminHome.html', 'saveform');

	}