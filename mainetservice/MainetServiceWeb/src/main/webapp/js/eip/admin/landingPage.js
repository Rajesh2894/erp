// <!-- script for scrolling statistics  START-->
$(document).ready(function(){
	
	$('.fancybox').fancybox();

});
ScrollRate = 100;
function scrollStatistics_init() {
	DivElmnt = document.getElementById('imp_statistic_scroll');
	ReachedMaxScroll = false;
	
	DivElmnt.scrollTop = 0;
	PreviousScrollTop  = 0;
	
	ScrollInterval = setInterval('scrollStatistics()', ScrollRate);
}

function scrollStatistics() {
	
	if (!ReachedMaxScroll) {
		DivElmnt.scrollTop = PreviousScrollTop;
		PreviousScrollTop++;
		
		ReachedMaxScroll = DivElmnt.scrollTop >= (DivElmnt.scrollHeight - DivElmnt.offsetHeight);
	}
	else {
		ReachedMaxScroll = (DivElmnt.scrollTop == 0)?false:true;
		
		DivElmnt.scrollTop = PreviousScrollTop;
		PreviousScrollTop--;
	}
}

function pauseStatistics() {
	clearInterval(ScrollInterval);
}

function resumeStatistics() {
	PreviousScrollTop = DivElmnt.scrollTop;
	ScrollInterval    = setInterval('scrollStatistics()', ScrollRate);
}


//<!-- script for scrolling statistics  END-->




function onDistrictChange(obj)
{
$('#selectedMunicipal').find('option:gt(0)').remove();
	var distid=$(obj).val();
	if(distid>0){
	var url	= 'Home.html?GetULBs';
	var data="distId="+distid;
	var lookUpList = __doAjaxRequest(url, 'post' ,data , false, 'json');
	var  optionsAsString='';
	$.each(lookUpList, function(index){
		var lookUp = lookUpList[index];
		optionsAsString +='<option value="'+lookUp.orgid+'" code="'+lookUp.orgShortNm+'">' + lookUp.orgname + '</option>';
		});
		$('#selectedMunicipal').append(optionsAsString);
}
}




function readMore(data) {
	var	errMsgDiv	=	'.msg-dialog-box';
	$(errMsgDiv).html(data);
	showModalBox(errMsgDiv);
	return false;
}

