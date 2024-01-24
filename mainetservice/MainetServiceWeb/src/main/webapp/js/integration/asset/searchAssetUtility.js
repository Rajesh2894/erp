var assetIdList = new Array();

function searchAsset(divName) {
	
	var searchURL = "AssetSearch.html";
	var response = __doAjaxRequest(searchURL + '?searchAsset', 'POST', {},
			false, 'html');
	$(divName).html(response);
	$('#searchAssetHome').on('click', 'tbody tr .selectedRow', function() {
		var value = $(this).attr("value");
		if (this.checked) {
			assetIdList.push(value);
		} else {
			assetIdList.pop(assetIdList.indexOf(value), 1);
		}
	});
}

function getAssetDetails() {
	
	var searchURL = "AssetSearch.html";
	var requestData = '';
	var response = null;
	if (assetIdList != 'undefined' && assetIdList.length > 0) {
		requestData = "assetIds=" + assetIdList.toString();
		response = __doAjaxRequest(searchURL + '?getAssetSummary', 'POST',
				requestData, false, 'json');
	}
	return response;
}