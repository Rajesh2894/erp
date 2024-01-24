$(function(e)
{
	var langIdForDiv = $('#langIDForDiv').val();
	
	if(langIdForDiv==2)
		{
			$(".EngDiv").attr("class","aboutUsEnglishDivAlignRLI");
			$(".RegDiv").attr("class","aboutUsRegionalDivAlignRLI");
		}
	
});

function countChar(val) {
	var maxlength=val.maxLength; 
	
    var len = val.value.length;
    if (len >= maxlength) {
      val.value = val.value.substring(0, maxlength);
      $('.charsRemaining').next('P').text(maxlength - len);
    } else {
    	$('.charsRemaining').next('P').text(maxlength - len);
    	$(this).siblings(".charsRemaining").show();
    }
  }; 