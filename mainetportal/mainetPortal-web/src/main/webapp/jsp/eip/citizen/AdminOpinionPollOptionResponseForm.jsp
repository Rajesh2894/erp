<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%  response.setContentType("text/html; charset=utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
	.overflow-hidden {
		overflow: hidden;
	}
	.back-btn > input[type='button'] {
		width: 100%;
	}
</style>
<script>
var flag = 0;
var chart;

window.onload = function() {
	
	if(localStorage.getItem('myKey')=='Polled'){
		
		$(".opinion-chart").show();
		$(".widget-content").hide();
		
		var id = $('#opinionIds').attr("value");
		var lngId = $('#langId').attr("value");
		var opdata = [];
		var response = __doAjaxRequest('AdminOpinionPollOptionResponseForm.html?ActivePollRsult&id='+id, 'GET', '', false);
		var tlCount=0;
		if(response !=null && response != '' && response!=undefined){
			var len = response.length;
			for(var i=0; i<len; i++){
		        var id = response[i].id;
		        var eng = response[i].eng;
		        var reg = response[i].reg;
		        var ocount = response[i].ocount;
		        tlCount=tlCount+ocount;
		       if(lngId === "1"){
						var op = {y:ocount,label:eng};
						opdata.push(op);
					}else if(lngId === "0"){
						var op = {y:ocount,label:reg};
						opdata.push(op);
					}
			} 
			 gpShow(opdata); 
			 $('#tlcnt').html(tlCount);
		}

	}else{
		$(".opinion-chart").hide();
		$(".widget-content").show();
		var opdata = [];
		gpShow(opdata);
	}
	
	
}

function gpShow(opdata){
	chart = new CanvasJS.Chart("opinionChartContainer", {
		animationEnabled: true,
			axisX: {
			labelAutoFit: true, 
			interval:1
			},
		data: [{
			type: "pie",
			responsive: true,
		    maintainAspectRatio: true,
			startAngle: 240,
			yValueFormatString: "##(0)\"\"",
			indexLabel: "{label}: #percent%",
			toolTipContent: "{label}: #percent%",
			indexLabelPlacement: "outside",
			indexLabelFontColor: "#000",
			indexLabelFontSize: 10,
			dataPoints: opdata,
		}]
	});
	setTimeout(function(){
		chart.render();
	},200);
}
	
function showGraph(ue){
	
	var id = $('#opinionIds').attr("value");
	var lngId = $('#langId').attr("value");
	var opdata = [];
	var response = __doAjaxRequest('AdminOpinionPollOptionResponseForm.html?ActivePollRsult&id='+id, 'GET', '', false);
	var tlCount=0;
	if(response !=null && response != '' && response!=undefined){
		var len = response.length;
		for(var i=0; i<len; i++){
	        var id = response[i].id;
	        var eng = response[i].eng;
	        var reg = response[i].reg;
	        var ocount = response[i].ocount;
	        tlCount=tlCount+ocount;
	       if(lngId === "1"){
					var op = {y:ocount,label:eng};
					opdata.push(op);
				}else if(lngId === "0"){
					var op = {y:ocount,label:reg};
					opdata.push(op);
				}
		} 
		 gpShow(opdata); 
		 $('#tlcnt').html(tlCount);
	}
	
}

function savePoll(ue){ 
	
	if (flag === 1) {
	    // do something here
		$(".widget-content").hide();
		doopinionpoll(ue);
		showGraph(ue);
		$(".opinion-chart").show();
		localStorage.setItem('myKey','Polled');
	}else{
		var errorList = [];
		errorList
				.push(getLocalMessage("poll.noselect.valid.msg"));
		showError(errorList);
	}
	$(this).scrollTop(0);
}

function doopinionpoll(obj) {
	
    var form = document.getElementById('frmCitizenOpinionPoll');
    var formData = $(form).serialize();
   
    var response = __doAjaxRequest('AdminOpinionPollOptionResponseForm.html?opinionpoll', 'POST', formData, false);
    
}
   function linkChange(ue){
	   flag = 1; 	
	}
   	
   
   function closeErrBox1()
   {
   	 $('.message').hide();
   }
   
   function countChar(val) {
       var len = val.value.length;
    
       if (len >= 1001) {
         val.value = val.value.substring(0, 1000);
       } else {
       	$('.charsRemaining').show();
       	$('.charsRemaining').next('P').text(1000 - len);
       
       }
     }
   
   function spanHide() {
     	$('.charsRemaining').hide();
     }
     
</script>
<div class="row padding-40 opinion-poll-main" id="CitizenService">
   <div class="col-xs-12 col-md-6 col-lg-6 col-sm-12 col-md-offset-3 col-lg-offset-3">
      <div class="login-panel">
       <c:choose>
	       <c:when test="${fn:length(command.entity.opinion.options) > 0}">
       <div class="widget  margin-bottom-0" id="content">
          <div class="widget-header">
             <h2>
                <strong>
                   <spring:message code="optionfeedback.formTitle" text="Opinion Poll"/>
                </strong>
             </h2>
          </div>
          <%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
          <div class="widget-content padding">
             <div class="message alert" role="alert" style="display: none"></div>
             <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
             <form:form method="post" action="AdminOpinionPollOptionResponseForm.html?opinionpoll"
                name="frmCitizenOpinionPoll" id="frmCitizenOpinionPoll">
                
                <c:set value="${fn:length(command.entity.opinionPollList)}" var="size"></c:set>
                <input type="hidden" name="opinionIds" value="${command.entity.opinion.id}" id="opinionIds">
                
                <input type="hidden" name="pollsize" value="${size}" id="pollsize">
                
                <c:forEach items="${command.entity.opinionPollList}" var="lookUp" varStatus="i">
                   		
							<input type="hidden" name="name${lookUp.id}" value="${lookUp.id}" id="id${i.count}">
							<input type="hidden" name="name${lookUp.eng}" value="${lookUp.eng}" id="eng${i.count}">
							<input type="hidden" name="name${lookUp.reg}" value="${lookUp.reg}" id="reg${i.count}">
							<input type="hidden" name="name${lookUp.ocount}" value="${lookUp.ocount}" id="ocount${i.count}">
							
                </c:forEach>
                
                <div class="form-group row">
                   <label for="fdUserName" class="col-sm-12">
                   <c:choose>
	                   <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
	                      <spring:message code="opinion.option" text="${command.entity.opinion.opinionEng}"/>
	                      <input type="hidden" name="langId" value="1" id="langId">
	                   </c:when>
	                   <c:otherwise>
	                      <spring:message code="opinion.option" text="${command.entity.opinion.oninionReg}"/>
	                       <input type="hidden" name="langId" value="0" id="langId">
	                   </c:otherwise>
					</c:choose>
                   </label>
                </div>
                <c:forEach items="${command.entity.opinion.options}" var="lookUp">
                <div class="form-group clear overflow-hidden row">
					<label for="fdUserName" class="control-label col-sm-6">
						<c:choose>
		                   <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<spring:message code="opinion.option" text="${lookUp.optionEn}"/>
							</c:when>
		                   <c:otherwise>
		                   		<spring:message code="opinion.option" text="${lookUp.optionRg}"/>
		                   </c:otherwise>
						</c:choose>
					</label>
					<div class="col-sm-6">
						<form:radiobutton path="entity.pOptionId"  name="linkType" value="${lookUp.oId}" onclick="linkChange(${lookUp.oId})" />
					</div>
                </div>
                </c:forEach>
                 <c:if test="${ not empty  command.entity.opinion.docPath}">
                 <div class="form-group clear overflow-hidden row" id="divid">
                		<label for="fdUserName" class="control-label col-sm-6">
						<spring:message code="opinion.file" text="Attachement"/>
						</label>
						<div class="col-sm-6">
	                		<%-- <c:set var='attachment' value= './cache/${command.entity.opinion.docPath}' /> --%>
	                		<c:set var="links" value="${fn:split(command.entity.opinion.docPath,',')}" /> 
						    <c:forEach items="${links}" var="downloadL" varStatus="count">
								<c:set var="lnk1" value="./cache/${downloadL}" />
								<a href="${lnk1}" target="_blank" class=""><i class="fa fa-file-pdf-o" style="color:red" ></i></a>
							</c:forEach>
						</div>	
                	
 		       	</div> 
 		       	</c:if>
 		       	 <c:if test="${ not empty  command.entity.opinion.imgPath}">
 		       	<div class="form-group img-tiles" id="divid">
 		       		<%-- <c:set var='imgFile' value= './cache/${command.entity.opinion.imgPath}' /> --%>
 		       		<%-- <a href="${imgFile}" target="_blank" class="">&nbsp;<i class="fa fa-picture-o" style="color:red" ></i>&nbsp;</a>
                	 --%>
                	 
                	  <c:set var="links" value="${fn:split(command.entity.opinion.imgPath,',')}" /> 
					    <c:forEach items="${links}" var="downloadL" varStatus="count">
							<c:set var="lnk1" value="${downloadL}" /> 
							<c:set var="lastName" value="${fn:split(lnk1, '/')}" />
							<c:set var="fname" value="${lastName[fn:length(lastName)-1]}" />
							
							<li>
								<apptags:filedownload filename="${fname}"
                                              filePath="${lnk1}" showFancyBox="true"
                                              actionUrl="SectionInformation.html?Download"></apptags:filedownload>
							</li>
						</c:forEach>
                	 
                	  <%-- <apptags:filedownload filename="${command.entity.opinion.imgPath}"
                                                filePath="${command.entity.opinion.imgPath}" showFancyBox="true"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload> --%>
 		       	</div>
                </c:if>
                <div class="row">
                	<div class="text-center">
                    <div class="col-xs-6">
                       <input type="button" class="btn btn-success btn-block"
                          onclick="savePoll(this);"
                          value="<spring:message code="opinion.submit" text="Submit"/>" />
                    </div>
                    <div class="col-xs-6 back-btn">
                       <apptags:backButton url="CitizenHome.html" cssClass="btn-block" buttonLabel="feedback.Back"></apptags:backButton>
                    </div>
                   </div>
                </div>
             </form:form>
          </div>
          <div class="opinion-chart">
          	<h2><spring:message code="opinion.graph.heading" text="Opinion Poll Graph"/></h2>
          	<div id="opinionChartContainer" class="opinion-chart-container"></div>
          	<div class="opinion-count-label"><spring:message code="opinion.poll.total.count" text="Total Count"/> : <span id="tlcnt"></span></div>
          </div>
       </div>
       </c:when>
      <c:otherwise>
      		<div class="datanot">
          	<h2><spring:message code="opinion.poll.not.available" text="Opinion Poll Not Available"/></h2>
          	
          </div>
      </c:otherwise>
	 </c:choose>
      </div>
   </div>
</div><hr/>