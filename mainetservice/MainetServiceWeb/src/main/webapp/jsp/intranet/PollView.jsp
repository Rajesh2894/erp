<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script> 
<script type="text/javascript" src="js/intranet/pollCreation.js"></script>  

<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="intranet.viewPoll" text="View Poll" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span></a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form id="pollViewForm" action="PollView.html" method="POST" class="form-horizontal" name="pollRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="warning-div error-div aaaaaalert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						 	<i class="fa fa-exclamation-circle"></i><span id="errorId"></span>
					</div>
					
					<%-- <h4>
						<spring:message code="intranet.pollVw" text="Poll View Details" />
					</h4> --%>

					<form:input type="hidden" path="pollRequest.pollQue" id="pollQue"/>
					<form:input type="hidden" path="pollRequest.pollid" id="pollid"/> 
					
				<div class="form-group">
					<div class="col-sm-4">
						<c:forEach items="${command.intranetPollObj}" var="intranetPollObj" varStatus="lk">
							<h2>Question : </h2>
						     <input type="hidden" name=myradio id="pollQue" value=""/> ${intranetPollObj}
						    <br/><h2>Answer : </h2>
							<c:forEach items="${command.intranetChoiceList}" var="intranetChoiceList" >
							   &nbsp &nbsp <form:radiobutton	name="mybox" path="" value="${intranetChoiceList.id}" id="pollAnsId"></form:radiobutton>${intranetChoiceList.text}<br>
							</c:forEach>
							<br/>
						</c:forEach>
					</div>	
				</div>

					<c:set var="checkLoggedEmpFromDb" value="${command.pollRequest.checkLoggedEmp}" scope="page"/> 
					<c:set var="checkLoggedEmpfromUi" value="${userSession.getCurrent().getEmployee().getEmpId()}" scope="page"/> 
					<c:set var="checkLoggedEmpFromDbChoiceVal" value="${command.pollRequest.choiceDescVal}" scope="page"/> 
					
					<div class="text-center">
						<c:if test="${(checkLoggedEmpFromDb eq checkLoggedEmpfromUi)}">
							You have already registered a vote for this poll !
						</c:if>	
						<c:if test="${(checkLoggedEmpfromUi ne checkLoggedEmpFromDb) and checkLoggedEmpFromDbChoiceVal eq 'Y'}">
							You have already registered a vote for this poll !
						</c:if>
						<c:if test="${command.intranetPollObj ne null && (checkLoggedEmpFromDbChoiceVal ne 'Y') }">
							<button type="button" class="btn btn-success" data-toggle="tooltip" data-original-title="Submit" id="proceedId" 
								onclick="savePollVeiwData(this)"> <spring:message text="Submit" />
							</button>
						</c:if>
						
						<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger hidden-print" value="Back">
					</div>	
				</form:form>
				
				</div>
		</div>
	</div>
</div> 

<!-- ashish test -->




