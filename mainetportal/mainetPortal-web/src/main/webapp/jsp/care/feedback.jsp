<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/script-library.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<link href="assets/libs/rating/star-rating.css" rel="stylesheet" type="text/css"/>
<script src="assets/libs/rating/star-rating.js"></script> 
<script>
function resetFeedbackForm(resetBtn) {
	if (resetBtn && resetBtn.form) {
		$('[id*=file_list]').html('');
		$('.error-div').hide();
		resetBtn.form.reset();		
	};
}
function closeErrorBox(){
	$('.error-div').hide();
}


</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content-page">
    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="care.feedback"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help" ><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="care.help"/></span></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="care.fieldwith"/> <i class="text-red-1">*</i><spring:message code="care.ismendatory"/></span></div>
          <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div><spring:message code="care.feedback.details"/>
           <form:form id="feedbackDetails" name="feedbackDetails" class="form-horizontal"
					method="POST" enctype="multipart/form-data">
            <div class="row">
              <div class="col-sm-6 col-sm-push-3">
                <div class="form-group">
                  <label class="control-label col-sm-4" for="rating-input"><spring:message code="care.feedback.rateus"/><span class="mand">*</span></label>
                  <div class="col-sm-8">
                    <form:input id="rating-input" type="number" path="feedbackDetails.ratings"/>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label col-sm-4 required-control"><spring:message code="care.feedback.tellusmore"/></label>
                  <div class="col-sm-8">
                    <form:textarea name="" cols="" rows="" onKeyUp="enforceMaxLength(this);" class="form-control" id="rating-content" path="feedbackDetails.ratingsContent"></form:textarea>
                  </div>
                </div>
                <div class="text-center">
                  <button type="button" class="btn btn-success" id="btnSave"><spring:message code="care.feedback.submit"/></button>
                  <button type="reset" class="btn btn-warning" onclick="resetFeedbackForm(this)"><spring:message code="care.feedback.reset"/></button>
                  <c:choose>
                  <c:when test="${DASH_FEEDBACK eq 'Y'}">
                   <button type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'"><spring:message code="care.back" /></button>
                  </c:when>
                  <c:otherwise>
                  <button type="button" class="btn btn-danger" onclick="javascript:openRelatedForm('grievance.html?getAllGrievanceRaisedByRegisteredCitizenView','this');"><spring:message code="care.feedback.back"/></button>
                 </c:otherwise>
               </c:choose>
                </div>
              </div>
            </div>
            
            <input type="hidden" name="" id="dashFeed" value="${DASH_FEEDBACK}"/>
            <input type="hidden" name="" id="hiddenTokenNumber" value="${tokenNumber}"/>
            <input type="hidden" name="feedbackDetails.id" id="hiddenFeedbackId" value="${command.feedbackDetails.id}"/>
             <input type="hidden" name="feedbackDetails.ratings" id="hiddenFeedbackRate" value="${command.feedbackDetails.ratingsStarCount}"/>
            
          </form:form >
        </div>
      </div>
    </div>
  </div>
<script>
$(document).ready(function() {
	debugger;
	$('.fancybox').fancybox();
	var star = $("#hiddenFeedbackRate").val();
	if(star !='' || star !=null){
		$('#rating-input').val(star);
	} 
	$("textarea").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	
	$("#btnSave").click(function(e) {
		var errorList=[];
		
		var ratinginput=$('#rating-input').val();
		var ratingcontent=$('#rating-content').val();
		var requestNo=$('#hiddenTokenNumber').val();
		var feedbackId=$("#hiddenFeedbackId").val();
		var dashFeed=$("#dashFeed").val();
		
		if (ratinginput == undefined || ratinginput=='0'|| ratinginput == "") {
			
			 errorList.push(getLocalMessage("care.validation.error.feedbackRating"));
		} 
		if (ratingcontent==''||ratingcontent == undefined) {
			
			 errorList.push(getLocalMessage("care.validation.error.feedback"));
		} 
		
		
		if (errorList.length==0) {
			 var submitfeedback = "grievance.html?saveFeedbackDetails";
			 var requestData ='requestNo='+requestNo+'&ratinginput='+ratinginput+'&ratingcontent='+ratingcontent+'&feedbackId='+feedbackId;
			 var response = __doAjaxRequest(submitfeedback,'post',requestData,false,'html');
			 if(!$.isEmptyObject(response)&& dashFeed !="Y"){
				 displayMessageOnSubmit(response);
			 }
			 else if(!$.isEmptyObject(response)){
				 displayMessageOnSubmitDashfeedBack(response);
			 }
			} else {
				displayErrorsOnPage (errorList);
				$('html,body').animate({ scrollTop: 0 }, 'slow');
			}
		return true;				
		
	});

	function displayMessageOnError(Msg){
		
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';	
		message	+='<p>'+Msg+'</p>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}

	function displayMessageOnSubmit(Msg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var closeBtnText = getLocalMessage('care.close');
		var message='';
		message += '<p class="text-blue-2 text-center padding-15">' + Msg
		+ '</p>';
		 message	+='<div class="text-center padding-bottom-10">'+	
	     '<button type="button" class=\'btn btn-blue-2\' onclick=\'javascript:openRelatedForm(\"grievance.html?getAllGrievanceRaisedByRegisteredCitizenView\",\"this\");\'>' +closeBtnText+ '</button>'+
	     '</div>'; 
			 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}
	
	function displayMessageOnSubmitDashfeedBack(Msg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var closeBtnText = getLocalMessage('care.close');
		var message='';
		message += '<p class="text-blue-2 text-center padding-15">' + Msg
		+ '</p>';
		 message	+='<div class="text-center padding-bottom-10">'+	
	     '<button type="button" class=\'btn btn-blue-2\' onclick=\'javascript:openRelatedForm(\"CitizenHome.html\",\"this\");\'>' +closeBtnText+ '</button>'+
	     '</div>'; 
			 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		
	}


	
	function displayErrorsOnPage(errorList) {

		var errMsg = '<button type="button" class="close" aria-label="Close" onclick="closeErrorBox()"><span aria-hidden="true">&times;</span></button>';
		errMsg += '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('.error-div').html(errMsg);
		$(".error-div").show();
		
		return false;
	}
	
});
</script> 
<script>
$(document).ready(function () {
	debugger;
	$('#rating-input').rating({
		  min: 0,
		  max: 5,
		  step: 1,
		  size: 'sm',
		  showClear: false
	});
	$('#rating-input').on('rating.change', function() {
		$('#rating-input').val();
	});
});


function enforceMaxLength(obj){
	if (obj.value.length > 500) {
		obj.value = obj.value.substring(0, 500);
	}
}

</script>