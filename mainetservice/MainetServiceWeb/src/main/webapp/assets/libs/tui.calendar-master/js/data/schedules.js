'use strict';

/*eslint-disable*/
var date;
var bade;
var e;
var str; 
var e= [];
var ScheduleList = [];
$(document).ready(function() {
	    
	
});

function getEvent(e) 
{
	
   var errorList = [];	
	   var advId=$('#advId option:selected').attr('value');
	   
	   var caseId=$('#caseId option:selected').attr('value');
	   //alert (advId);
	   if (advId == '' || advId == undefined) {
			errorList.push(getLocalMessage("lgl.validate.advName"));			
		}	   
     if (advId != '' || advId != undefined){
	  if (caseId == '' || caseId == undefined) {
		errorList.push(getLocalMessage("lgl.validate.suiteNo"));		
	   }  
	  }
    
	if (errorList.length == 0) {
	    var events = [];
	    var URL ='EventDate.html?eventDate';
	    var requestData = {
	    		"caseId" : caseId,
	    		"advId" : advId
	    	    };
	   
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		//var returnData=[{"date":"2021-10-22","description":"Judgement/case Result of Case No 7"},{"date":"2021-10-17","description":"Hearing of Case No 7"},{"date":"2021-10-11","description":"Hearing of Case No 7"}];
		var result = [];
	
		var prePopulate = JSON.parse(returnData);
		//var prePopulate = returnData;
	}else {
		displayErrorsOnPage(errorList);	
		return false;
	}
		
		

$.each(prePopulate, function(index) {
		
		var obj = prePopulate[index];
		date = obj.date;
		var startDate=date+'T09:00:00';
		var endDate=date+'T14:00:00'
		//alert(date);
		
		var caseno=obj.caseNo;
		var advName=obj.advocateName;
		var courtName=obj.courtName;
		var desc = "<strong>Case No :  </strong>"+caseno+"<br />"+" <strong>Court Name :  </strong>"+courtName+"<br /><strong> Case Description : </strong> <br />"+obj.description;
		ScheduleList.push({
			id: index,
	        calendarId: '1',
	        title: advName,
	        body: desc,
	        dueDateClass:'',
	        category: 'time',
	        dueDateClass: '',
	        start: new Date(startDate),
	        end: new Date(endDate),
	        color: '#FFFFFF',
	        bgColor:'#FF0000',
	        raw: {
                location: courtName
            },
	        isReadOnly: true   // schedule is read-only
		});
			
		//alert(date+"-"+desc+"-"+caseno+"-"+advName+"-"+courtName);
	});
		
		
		
		    var divName = '.content-page';
		    var requestData = { };
		    var ajaxResponse = doAjaxLoading("EventDate.html"+ '?' +"viewCalender", requestData,
			    'html', divName);
		    $(divName).removeClass('ajaxloader');
		    $(divName).html(ajaxResponse);
		   // prepareTags();
		 
	
		
		 

}


function generateSchedule(viewName, renderStart, renderEnd) {
	   // ScheduleList = [];
	    CalendarList.forEach(function(calendar) {
	        var i = 0, length = 10;
	        if (viewName === 'month') {
	            length = 3;
	        } else if (viewName === 'day') {
	            length = 4;
	        }
	       /* for (; i < length; i += 1) {
	            generateRandomSchedule(calendar, renderStart, renderEnd);
	        }*/
	    });
	}
