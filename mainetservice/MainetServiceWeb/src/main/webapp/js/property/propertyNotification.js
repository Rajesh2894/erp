function SearchProperties(element) {
	showloader(true);
	setTimeout(function() {
		sendNotifiations(element)
	}, 2);
	showloader(false);
}

function sendNotifiations(element) {

	return saveOrUpdateForm(element, "", 'PropertyNotification.html',
			'sendNotification');
}

function resetButton() {

	var data = {};
	var URL = 'PropertyNotification.html?propertyNotificationReset';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}