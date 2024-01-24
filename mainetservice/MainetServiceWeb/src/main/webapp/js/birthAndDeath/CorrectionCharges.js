$(document).ready(function() {

	$("footer").hide();

});


function saveAndGenerateLoi(obj) {
	debugger;
	return saveOrUpdateForm(obj,
			getLocalMessage('mrm.approve.create.success'), 'AdminHome.html',
			'generateLOIForBND');
}
